package ua.training.model.services;


import ua.training.model.dao.*;
import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.*;
import ua.training.model.exceptions.NotEnoughMoneyException;
import ua.training.model.utils.Page;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static ua.training.model.utils.MyConstants.*;

/**
 * Service for processing orders
 */

public class OrderService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    private final CalculationService calculationService;

    private final UserService userService;

    private final TaxiService taxiService;

    private final QueryService queryService;

    public OrderService(CalculationService calculationService, UserService userService, TaxiService taxiService, QueryService queryService) {
        this.calculationService = calculationService;
        this.userService = userService;
        this.taxiService = taxiService;
        this.queryService = queryService;
    }

    public Order prepareOrder(String initialAddress, String finishAddress, int capacity, Set<Taxi> taxi, User user) {
        BigDecimal distance = calculationService.calculateTimeOrDistance(initialAddress + finishAddress, AVERAGE_DISTANCE, 2);
        BigDecimal multiplier = taxi.stream().map(t -> t.getTaxiClass().getMultiplier()).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        TaxiStatus taxiStatus = getTaxiStatus("BUSY");
        taxi.forEach(t -> t.setTaxiStatus(taxiStatus));
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(getOrderStatus("ACTIVE"));
        order.setTotal(calculationService.calculateTotal(multiplier.doubleValue(), user.getDiscount().doubleValue(), distance.doubleValue()));
        order.setAddressFrom(initialAddress);
        order.setAddressTo(finishAddress);
        order.setDistance(distance);
        order.setTime(calculationService.calculateTimeOrDistance(initialAddress, AVERAGE_TIME, 0));
        order.setPeopleAmount(capacity);
        order.setTaxi(taxi);
        return order;
    }

    public int saveOrder(Order order, Connection connection) {
        try {
            connection.setAutoCommit(false);
            OrderDAO orderDAO = daoFactory.createOrderDAO(connection);
            userService.getMoneyFromUser(order.getTotal(), order.getUser().getId(), connection, false);
            order.getTaxi().forEach(t -> taxiService.updateTaxiStatus(t, t.getTaxiStatus().getName(), true,
                    connection, false));
            orderDAO.create(order);
            order.getTaxi().forEach(t -> orderDAO.mapOrderAndTaxi(order.getId(), t.getId()));
            connection.commit();
            return ORDER_SAVED;
        } catch (Exception exception) {
            int errorCode = exception.getClass().equals(NotEnoughMoneyException.class) ? NOT_ENOUGH_MONEY : DATA_DEPRECATED;
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                //TODO log
            }
            return errorCode;
        }
    }

    public Page<Order> getPaginatedOrders(User user, Integer limit, Integer page, String sortField,
                                          String sortDirection, boolean isSearchByDate, String date,
                                          boolean isSearchByName, String name, String surname,
                                          Connection connection) {
        try {
            connection.setAutoCommit(false);
            OrderDAO orderDAO = daoFactory.createOrderDAO(connection);
            TaxiDAO taxiDAO = daoFactory.createTaxiDAO(connection);
            String userFilterQuery = queryService.prepareUserFilterQuery(user, isSearchByName, name, surname);
            String dateFilterQuery = queryService.prepareDateFilterQuery(isSearchByDate, date);
            int recordsAmount = orderDAO.countOrders(userFilterQuery, dateFilterQuery);
            int offset = limit * page;
            int pages = recordsAmount / limit + ((recordsAmount % limit == 0) ? 0 : 1);
            if (page > pages || page < 0) {
                throw new Exception();
            }
            Set<Order> orders = orderDAO.findLimitedSortedOrders(limit, offset, sortField, sortDirection, userFilterQuery, dateFilterQuery);
            orders.forEach(o -> o.setTaxi(taxiDAO.findByOrderId(o.getId())));
            connection.commit();
            return new Page<>(orders, page, 0, pages - 1, sortField, sortDirection, isSearchByDate,
                    date, isSearchByName, name, surname);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }

    }

    public boolean processOrder(Long orderId, Long userId, Connection connection, boolean delete) {
        try {
            connection.setAutoCommit(false);
            OrderDAO orderDAO = daoFactory.createOrderDAO(connection);
            TaxiDAO taxiDAO = daoFactory.createTaxiDAO(connection);
            OrderStatusDAO orderStatusDAO = daoFactory.createOrderStatusDAO(connection);
            Order order = orderDAO.findById(orderId);
            order.setTaxi(taxiDAO.findByOrderId(orderId));
            order.getTaxi().forEach(t -> taxiService.updateTaxiStatus(t, "AVAILABLE", false, connection, false));
            if (delete) {
                order.setOrderStatus(orderStatusDAO.findOrderStatusByName("CANCELED").orElseThrow(RuntimeException::new));
                userService.updateUserBalance(userId, order.getTotal().subtract(BigDecimal.valueOf(INITIAL_PRICE)), connection, false);
            } else {
                order.setOrderStatus(orderStatusDAO.findOrderStatusByName("DONE").orElseThrow(RuntimeException::new));
                userService.increaseDiscount(userId, connection, false);
            }
            boolean result = orderDAO.update(order);
            connection.commit();
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                //TODO log
            }
        }
        return false;
    }

    private TaxiStatus getTaxiStatus(String name) {
        Connection connection = ConnectionManager.getConnection();
        TaxiStatusDAO taxiStatusDAO = daoFactory.createTaxiStatusDAO(connection);
        TaxiStatus taxiStatus = taxiStatusDAO.findTaxiStatusByName(name).orElseThrow(IllegalArgumentException::new);
        ConnectionManager.close(connection);
        return taxiStatus;
    }

    private OrderStatus getOrderStatus(String name) {
        Connection connection = ConnectionManager.getConnection();
        OrderStatusDAO orderStatusDAO = daoFactory.createOrderStatusDAO(connection);
        OrderStatus orderStatus = orderStatusDAO.findOrderStatusByName(name).orElseThrow(IllegalArgumentException::new);
        ConnectionManager.close(connection);
        return orderStatus;
    }
}

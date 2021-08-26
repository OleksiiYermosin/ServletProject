package ua.training.model.services;


import ua.training.model.dao.*;
import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.*;
import ua.training.model.exceptions.NotEnoughMoneyException;

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

    public OrderService(CalculationService calculationService, UserService userService, TaxiService taxiService) {
        this.calculationService = calculationService;
        this.userService = userService;
        this.taxiService = taxiService;
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

    public int saveOrder(Order order) {
        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            OrderDAO orderDAO = daoFactory.createOrderDAO(connection);
            userService.getMoneyFromUser(order.getTotal(), order.getUser().getId(), connection, false);
            order.getTaxi().forEach(t -> taxiService.updateTaxiStatus(t, t.getTaxiStatus().getName(), true,
                    ConnectionManager.getConnection(), false));
            orderDAO.create(order);
            order.getTaxi().forEach(t -> orderDAO.mapOrderAndTaxi(order.getId(), t.getId()));
            userService.increaseDiscount(order.getUser().getId(), connection, false);
            connection.commit();
            return ORDER_SAVED;
        } catch (Exception exception) {
            int errorCode = exception.getClass().equals(NotEnoughMoneyException.class) ? NOT_ENOUGH_MONEY : DATA_DEPRECATED;
            try {
                connection.rollback();
                System.out.println("RollBack");
            } catch (SQLException sqlException) {
                //TODO log
            }
            return errorCode;
        }
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


/*

    private final OrderStatusRepository orderStatusRepository;


    private final TaxiStatusRepository taxiStatusRepository;

    private final TaxiService taxiService;

    private final UserService userService;

    private final CalculationService calculationService;



    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, TaxiService taxiService,
                        UserService userService, TaxiStatusRepository taxiStatusRepository, CalculationService calculationService) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.taxiService = taxiService;
        this.userService = userService;
        this.taxiStatusRepository = taxiStatusRepository;
        this.calculationService = calculationService;
    }

    public Page<Order> getPaginatedOrders(PageInfoDTO pageInfoDTO, Predicate predicate) {
        logger.info("Getting pages with sort and filter");
        Pageable pageable = PageRequest.of(pageInfoDTO.getPage(), MyConstants.PAGE_SIZE,
                pageInfoDTO.getSortDirection().equals("desc") ? Sort.by(pageInfoDTO.getSort()).descending() : Sort.by(pageInfoDTO.getSort()).ascending());
        return (predicate == null ? orderRepository.findAll(pageable) : orderRepository.findAll(predicate, pageable));
    }


    public Page<Order> getPaginatedOrdersByUser(Integer page, String sort, String sortDirection, User user) {
        logger.info("Getting sorted pages");
        Pageable pageable = PageRequest.of(page, MyConstants.PAGE_SIZE,
                sortDirection.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending());
        return orderRepository.findAllByUser(user, pageable);
    }



    public Long processOrder(Long orderId, boolean delete) {
        Order order = orderRepository.getById(orderId);
        order.getTaxi().forEach(t -> taxiService.updateTaxiStatus(t, taxiStatusRepository.findByName("AVAILABLE").orElseThrow(StatusNotFoundException::new), false));
        if (delete) {
            order.setOrderStatus(orderStatusRepository.findByName("CANCELED").orElseThrow(StatusNotFoundException::new));
            userService.updateUserBalance(order.getUser().getId(), order.getTotal().subtract(BigDecimal.valueOf(MyConstants.INITIAL_PRICE)));
        } else {
            order.setOrderStatus(orderStatusRepository.findByName("DONE").orElseThrow(StatusNotFoundException::new));
            userService.increaseDiscount(order.getUser().getId());
        }
        return orderRepository.save(order).getId();
    }


    public Predicate makePredicate(PageInfoDTO pageInfoDTO) {
        logger.info("Creating predicate");
        return FilterPredicate.builder().add(pageInfoDTO.isSearchByName() && !pageInfoDTO.getName().isEmpty() ? pageInfoDTO.getName() : null, QOrder.order.user.name::eq)
                .add(pageInfoDTO.isSearchByName() && !pageInfoDTO.getSurname().isEmpty() ? pageInfoDTO.getSurname() : null, QOrder.order.user.surname::eq)
                .add(pageInfoDTO.isSearchByDate() ? LocalDate.parse(pageInfoDTO.getDate()) : null, QOrder.order.date::eq).build();
    }

*/

}

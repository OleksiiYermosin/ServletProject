package ua.training.model.dao.impl;

import ua.training.model.dao.OrderDAO;
import ua.training.model.dao.mappers.EagerOrderMapper;
import ua.training.model.entities.Order;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JDBCOrderDAO implements OrderDAO {

    private final Connection connection;

    public JDBCOrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean mapOrderAndTaxi(Long orderId, Long taxiId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_taxi(order_id, taxi_id)" +
                " VALUES (?, ?);")) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, taxiId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            return false;
        }
        return true;
    }

    @Override
    public Integer countOrders(String userFilterQuery, String dateFilterQuery) {
        try (PreparedStatement ps = connection.prepareCall("SELECT COUNT(user_id) FROM orders WHERE user_id " + userFilterQuery + " " +
                "AND date "+ dateFilterQuery +";")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return -1;
    }

    @Override
    public Set<Order> findLimitedSortedOrders(Integer limit, Integer offset, String sortField, String sortDirection,
                                              String userFilterQuery, String dateFilterQuery) {
        Set<Order> result = new LinkedHashSet<>();
        try (PreparedStatement ps = connection.prepareCall("SELECT orders.id, orders.total, orders.date, orders.address_from, orders.address_to, " +
                "orders.distance, orders.people, order_statuses.id AS order_status_id, order_statuses.name AS order_status_name, " +
                "users.id AS user_id, users.name AS user_name, users.surname AS user_surname " +
                "FROM orders JOIN order_statuses ON orders.status_id=order_statuses.id AND orders.user_id " + userFilterQuery + " AND date " + dateFilterQuery +
                " JOIN users ON orders.user_id=users.id " +
                "ORDER BY " + sortField + " " + sortDirection + " LIMIT ? OFFSET ?;")) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new EagerOrderMapper().extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public boolean create(Order entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders(user_id, status_id, total, address_from, address_to, distance, people)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setLong(2, entity.getOrderStatus().getId());
            preparedStatement.setBigDecimal(3, entity.getTotal());
            preparedStatement.setString(4, entity.getAddressFrom());
            preparedStatement.setString(5, entity.getAddressTo());
            preparedStatement.setBigDecimal(6, entity.getDistance());
            preparedStatement.setInt(7, entity.getPeopleAmount());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
            }
            resultSet.close();
        } catch (SQLException exception) {
            return false;
        }
        return true;
    }

    @Override
    public Order findById(Long id) {
        try (PreparedStatement ps = connection.prepareCall("SELECT orders.id, orders.total, orders.date, orders.address_from, " +
                "orders.address_to, orders.distance, orders.people,\n" +
                "order_statuses.id AS order_status_id, order_statuses.name AS order_status_name,\n" +
                "users.id AS user_id, users.name AS user_name, users.surname AS user_surname\n" +
                "FROM orders JOIN order_statuses ON orders.status_id=order_statuses.id AND orders.id=?\n" +
                "JOIN users ON orders.user_id = users.id;")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new EagerOrderMapper().extractFromResultSet(rs);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean update(Order entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET status_id=?, total=?, " +
                    "date=?, address_from=?, address_to=?, distance=?, people=? WHERE id=?;");
            preparedStatement.setLong(1, entity.getOrderStatus().getId());
            preparedStatement.setBigDecimal(2, entity.getTotal());
            preparedStatement.setDate(3, Date.valueOf(entity.getDate()));
            preparedStatement.setString(4, entity.getAddressFrom());
            preparedStatement.setString(5, entity.getAddressTo());
            preparedStatement.setBigDecimal(6, entity.getDistance());
            preparedStatement.setInt(7, entity.getPeopleAmount());
            preparedStatement.setLong(8, entity.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void delete(Long id) {

    }

}

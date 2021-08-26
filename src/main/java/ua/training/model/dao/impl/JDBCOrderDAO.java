package ua.training.model.dao.impl;

import ua.training.model.dao.OrderDAO;
import ua.training.model.entities.Order;

import java.sql.*;
import java.util.List;

public class JDBCOrderDAO implements OrderDAO {

    private final Connection connection;

    public JDBCOrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean mapOrderAndTaxi(Long orderId, Long taxiId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_taxi(order_id, taxi_id)" +
                " VALUES (?, ?);")){
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, taxiId);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            return false;
        }
        return true;
    }

    @Override
    public boolean create(Order entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders(user_id, status_id, total, address_from, address_to, distance, people)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setLong(2, entity.getOrderStatus().getId());
            preparedStatement.setBigDecimal(3, entity.getTotal());
            preparedStatement.setString(4, entity.getAddressFrom());
            preparedStatement.setString(5, entity.getAddressTo());
            preparedStatement.setBigDecimal(6, entity.getDistance());
            preparedStatement.setInt(7, entity.getPeopleAmount());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                entity.setId(resultSet.getLong("id"));
            }
            resultSet.close();
        }catch (SQLException exception){
            return false;
        }
        return true;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }

}

package ua.training.model.dao.impl;

import ua.training.model.dao.OrderStatusDAO;
import ua.training.model.dao.mappers.OrderStatusMapper;
import ua.training.model.dao.mappers.TaxiStatusMapper;
import ua.training.model.entities.Order;
import ua.training.model.entities.OrderStatus;
import ua.training.model.entities.TaxiStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCOrderStatusDAO implements OrderStatusDAO {

    private final Connection connection;

    public JDBCOrderStatusDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Order entity) {
        return false;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public Optional<OrderStatus> findOrderStatusByName(String name) {
        Optional<OrderStatus> result = Optional.empty();
        try(PreparedStatement ps = connection.prepareCall("SELECT * FROM order_statuses WHERE name = ?")){
            ps.setString( 1, name);
            ResultSet rs = ps.executeQuery();
            OrderStatusMapper mapper = new OrderStatusMapper();
            if (rs.next()){
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
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

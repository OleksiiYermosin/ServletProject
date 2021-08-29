package ua.training.model.dao.mappers;

import ua.training.model.entities.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusMapper implements ObjectMapper<OrderStatus>{

    @Override
    public OrderStatus extractFromResultSet(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(rs.getLong("id"));
        orderStatus.setName(rs.getString("name"));
        return orderStatus;
    }

}

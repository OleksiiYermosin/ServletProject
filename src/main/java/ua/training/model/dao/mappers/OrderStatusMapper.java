package ua.training.model.dao.mappers;

import ua.training.model.entities.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderStatusMapper implements ObjectMapper<OrderStatus>{

    @Override
    public OrderStatus extractFromResultSet(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(rs.getLong("id"));
        orderStatus.setName(rs.getString("name"));
        return orderStatus;
    }

    @Override
    public OrderStatus makeUnique(Map<Long, OrderStatus> cache, OrderStatus object) {
        cache.putIfAbsent(object.getId(), object);
        return cache.get(object.getId());
    }
}

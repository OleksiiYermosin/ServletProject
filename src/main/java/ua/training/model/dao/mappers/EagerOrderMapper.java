package ua.training.model.dao.mappers;

import ua.training.model.entities.Order;
import ua.training.model.entities.OrderStatus;
import ua.training.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EagerOrderMapper implements ObjectMapper<Order>{

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setTotal(rs.getBigDecimal("total"));
        order.setDate(rs.getDate("date").toLocalDate());
        order.setAddressFrom(rs.getString("address_from"));
        order.setAddressTo(rs.getString("address_to"));
        order.setDistance(rs.getBigDecimal("distance"));
        order.setPeopleAmount(rs.getInt("people"));
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(rs.getLong("order_status_id"));
        orderStatus.setName(rs.getString("order_status_name"));
        order.setOrderStatus(orderStatus);
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        user.setSurname(rs.getString("user_surname"));
        order.setUser(user);
        return order;
    }

}

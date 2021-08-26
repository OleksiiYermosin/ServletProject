package ua.training.model.dao;

import ua.training.model.entities.Order;
import ua.training.model.entities.OrderStatus;

import java.util.Optional;

public interface OrderStatusDAO extends GenericDAO<Order>{

    Optional<OrderStatus> findOrderStatusByName(String name);

}

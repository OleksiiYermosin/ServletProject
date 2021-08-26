package ua.training.model.dao;

import ua.training.model.entities.Order;

public interface OrderDAO extends GenericDAO<Order>{

    boolean mapOrderAndTaxi(Long orderId, Long txiId);
}

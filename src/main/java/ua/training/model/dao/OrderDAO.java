package ua.training.model.dao;

import ua.training.model.entities.Order;

import java.util.Set;

public interface OrderDAO extends GenericDAO<Order>{

    boolean mapOrderAndTaxi(Long orderId, Long txiId);

    Integer countOrders(String userFilterQuery, String dateFilterQuery);

    Set<Order> findLimitedSortedOrders(Integer limit, Integer offset, String sortField, String sortDirection,
                                       String userFilterQuery, String dateFilterQuery);
}

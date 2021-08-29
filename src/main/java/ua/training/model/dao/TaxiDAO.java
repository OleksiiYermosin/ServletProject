package ua.training.model.dao;

import ua.training.model.entities.Taxi;

import java.util.Optional;
import java.util.Set;

public interface TaxiDAO extends GenericDAO<Taxi>{

    Optional<Taxi> findSuitableCab(String taxiClassName, String taxiStatusName, int capacity);

    Set<Taxi> findTaxiOfAnotherClass(String taxiStatus, int capacity);

    Integer findCapacitySum();

    Set<Taxi> findByOrderId(Long orderId);

    Set<Taxi> findAllTaxiByStatus(String taxiStatus);

}

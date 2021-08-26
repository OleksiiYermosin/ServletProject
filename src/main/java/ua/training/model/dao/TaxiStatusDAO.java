package ua.training.model.dao;

import ua.training.model.entities.TaxiStatus;

import java.util.Optional;

public interface TaxiStatusDAO extends GenericDAO<TaxiStatus> {

    Optional<TaxiStatus> findTaxiStatusByName(String name);

}

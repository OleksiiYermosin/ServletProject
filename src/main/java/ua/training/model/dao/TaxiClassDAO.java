package ua.training.model.dao;

import ua.training.model.entities.TaxiClass;

import java.util.Optional;

public interface TaxiClassDAO extends GenericDAO<TaxiClass> {

    Optional<Long> findTaxiClassIdByName(String name);
}

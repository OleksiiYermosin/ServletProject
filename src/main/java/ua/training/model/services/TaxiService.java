package ua.training.model.services;


import ua.training.model.dao.*;
import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.Taxi;
import ua.training.model.entities.TaxiStatus;

import java.sql.Connection;
import java.util.*;

/**
 * Service for processing taxi
 */
public class TaxiService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    /**
     * Logger
     */


    public boolean updateTaxiStatus(Taxi taxiToUpdate, String taxiStatus, boolean checkOnAvailability, Connection connection, boolean close) {
        TaxiDAO taxiDAO = daoFactory.createTaxiDAO(connection);
        Taxi taxi = taxiDAO.findById(taxiToUpdate.getId());
        System.out.println(taxiToUpdate);
        System.out.println(taxi);
        if (checkOnAvailability && !taxi.getTaxiStatus().getName().equals("AVAILABLE")) {
            throw new IllegalArgumentException("Taxi is busy");
        }
        taxi.setTaxiStatus(getTaxiStatus(taxiStatus, connection, close));
        taxiDAO.update(taxi);
        if (close){
            ConnectionManager.close(connection);
        }
        return true;
    }

    /**
     * Method for search suitable car
     *
     * @return optional of taxi
     */
    public Optional<Taxi> findSuitableCar(String taxiClassString, String taxiStatusString, int capacity, Connection connection, boolean close) {
        TaxiDAO taxiDAO = daoFactory.createTaxiDAO(connection);
        Optional<Taxi> result = taxiDAO.findSuitableCab(taxiClassString, taxiStatusString, capacity);
        if (close){
            ConnectionManager.close(connection);
        }
        return result;
    }

    public Set<Taxi> findTaxiOfAnotherClass(String taxiStatus, int capacity, Connection connection, boolean close) {
        TaxiDAO taxiDAO = daoFactory.createTaxiDAO(connection);
        Set<Taxi> result = taxiDAO.findTaxiOfAnotherClass(taxiStatus, capacity);
        if (close){
            ConnectionManager.close(connection);
        }
        return result;
    }

    public Set<Taxi> findSeveralTaxi(int capacity, Connection connection, boolean close) {
        Set<Taxi> taxiResult = new HashSet<>();
        TaxiDAO taxiDAO = daoFactory.createTaxiDAO(connection);
        if (taxiDAO.findCapacitySum() < capacity) {
            if (close){
                ConnectionManager.close(connection);
            }
            return taxiResult;
        }
        List<Taxi> taxiFromDb = new ArrayList<>(taxiDAO.findAllTaxiByStatus("AVAILABLE"));
        for (int i = 0, realCapacity = 0; realCapacity < capacity; i++) {
            taxiResult.add(taxiFromDb.get(i));
            realCapacity += taxiFromDb.get(i).getCapacity();
        }
        if (close){
            ConnectionManager.close(connection);
        }
        return taxiResult;
    }

    private TaxiStatus getTaxiStatus(String name, Connection connection, boolean close) {
        TaxiStatusDAO taxiStatusDAO = daoFactory.createTaxiStatusDAO(connection);
        TaxiStatus taxiStatus = taxiStatusDAO.findTaxiStatusByName(name).orElseThrow(IllegalArgumentException::new);
        if (close){
            ConnectionManager.close(connection);
        }
        return taxiStatus;
    }


    /*public boolean saveTaxi(Taxi taxi) {
        try {
            taxiRepository.findByInfo(taxi.getInfo()).orElseThrow(IllegalArgumentException::new);
            return false;
        } catch (IllegalArgumentException exception) {
            taxiRepository.save(taxi);
        }
        return true;
    }*/
}

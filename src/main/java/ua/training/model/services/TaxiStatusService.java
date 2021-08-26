package ua.training.model.services;

import ua.training.model.dao.DAOFactory;
import ua.training.model.dao.TaxiStatusDAO;
import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.TaxiStatus;

import java.sql.Connection;

public class TaxiStatusService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public TaxiStatus findTaxiStatusById(Long id) throws Exception {
        Connection connection = ConnectionManager.getConnection();
        TaxiStatusDAO taxiStatusDAO = daoFactory.createTaxiStatusDAO(connection);
        TaxiStatus taxiStatus = taxiStatusDAO.findById(id);
        ConnectionManager.close(connection);
        return taxiStatus;
    }

}

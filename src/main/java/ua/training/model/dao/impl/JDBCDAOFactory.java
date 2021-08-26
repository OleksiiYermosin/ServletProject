package ua.training.model.dao.impl;

import ua.training.model.dao.*;


import java.sql.Connection;

public class JDBCDAOFactory extends DAOFactory {

    @Override
    public UserDAO createUserDAO(Connection connection) {
        return new JDBCUserDAO(connection);
    }

    @Override
    public TaxiDAO createTaxiDAO(Connection connection) {
        return new JDBCTaxiDAO(connection);
    }

    @Override
    public TaxiStatusDAO createTaxiStatusDAO(Connection connection) {
        return new JDBCTaxiStatusDAO(connection);
    }

    @Override
    public TaxiClassDAO createTaxiClassDAO(Connection connection) {
        return new JDBCTaxiClassDAO(connection);
    }

    @Override
    public OrderStatusDAO createOrderStatusDAO(Connection connection) {
        return new JDBCOrderStatusDAO(connection);
    }

    @Override
    public OrderDAO createOrderDAO(Connection connection){
        return new JDBCOrderDAO(connection);
    }


}

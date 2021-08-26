package ua.training.model.dao;

import ua.training.model.dao.impl.JDBCDAOFactory;

import java.sql.Connection;

public abstract class DAOFactory {

    private static DAOFactory daoFactory;

    public abstract UserDAO createUserDAO(Connection connection);

    public abstract TaxiDAO createTaxiDAO(Connection connection);

    public abstract TaxiStatusDAO createTaxiStatusDAO(Connection connection);

    public abstract TaxiClassDAO createTaxiClassDAO(Connection connection);

    public abstract OrderStatusDAO createOrderStatusDAO(Connection connection);

    public abstract OrderDAO createOrderDAO(Connection connection);

    public static DAOFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DAOFactory.class){
                if(daoFactory==null){
                    daoFactory = new JDBCDAOFactory();
                }
            }
        }
        return daoFactory;
    }

}

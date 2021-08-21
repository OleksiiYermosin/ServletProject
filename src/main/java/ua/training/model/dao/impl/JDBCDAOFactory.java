package ua.training.model.dao.impl;

import ua.training.model.dao.DAOFactory;
import ua.training.model.dao.UserDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDAOFactory extends DAOFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDAO createUserDAO() {
        return new JDBCUserDAO(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

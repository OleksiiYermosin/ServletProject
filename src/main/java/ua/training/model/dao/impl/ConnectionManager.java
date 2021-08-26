package ua.training.model.dao.impl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionManager {

    private static final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException sqlException) {
            throw new RuntimeException();
        }
    }

    public static boolean close(Connection connection){
        try {
            connection.close();
            return true;
        } catch (NullPointerException | SQLException sqlException) {
            return false;
        }
    }

}

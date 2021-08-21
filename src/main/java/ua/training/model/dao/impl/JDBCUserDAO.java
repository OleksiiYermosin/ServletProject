package ua.training.model.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ua.training.model.dao.UserDAO;
import ua.training.model.entities.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {

    private final Connection connection;

    public JDBCUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {
        PreparedStatement preparedStatement = null;
        try{
        preparedStatement = connection.prepareStatement("INSERT INTO users(username, password, name, surname, phone, role_id)" +
                " VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getUsername());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getName());
        preparedStatement.setString(4, entity.getSurname());
        preparedStatement.setString(5, entity.getPhone());
        preparedStatement.setInt(6, 1);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        } catch (SQLException sqlException){
            System.out.println("User wasn`t saved");
        }finally {
            close();
        }
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            System.out.println("Connection wasn`t closed");
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.empty();
    }
}

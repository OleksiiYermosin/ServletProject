package ua.training.model.services;

import ua.training.model.dao.DAOFactory;
import ua.training.model.dao.UserDAO;
import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.User;
import ua.training.model.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.sql.Connection;

import static ua.training.model.utils.MyConstants.*;

public class UserService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public boolean saveUser(User user, Connection connection, boolean close) {
        UserDAO userDAO = daoFactory.createUserDAO(connection);
        boolean result = userDAO.create(user);
        if (close) {
            ConnectionManager.close(connection);
        }
        return result;
    }

    public User findUserByUsername(String username, Connection connection, boolean close) {
        UserDAO userDAO = daoFactory.createUserDAO(connection);
        User user = userDAO.findUserByUsername(username).orElseThrow(IllegalArgumentException::new);
        if (close) {
            ConnectionManager.close(connection);
        }
        return user;
    }

    public User findUserById(Long id, Connection connection, boolean close) {
        UserDAO userDAO = daoFactory.createUserDAO(connection);
        User user = userDAO.findById(id);
        if (close) {
            ConnectionManager.close(connection);
        }
        return user;
    }

    public boolean updateUserBalance(long id, BigDecimal value, Connection connection, boolean close) {
        UserDAO userDAO = daoFactory.createUserDAO(connection);
        User user = userDAO.findById(id);
        user.setBalance(user.getBalance().add(value));
        boolean result = userDAO.update(user);
        if (close) {
            ConnectionManager.close(connection);
        }
        return result;
    }

    public boolean increaseDiscount(Long id, Connection connection, boolean close) {
        UserDAO userDAO = daoFactory.createUserDAO(connection);
        User user = userDAO.findById(id);
        user.setDiscount(user.getDiscount().add(BigDecimal.valueOf(DISCOUNT_STEP)));
        boolean result = false;
        if (user.getDiscount().compareTo(BigDecimal.valueOf(30.0)) <= 0) {
            userDAO.update(user);
            result = true;
        }
        if (close) {
            ConnectionManager.close(connection);
        }
        return result;
    }

    public boolean getMoneyFromUser(BigDecimal total, Long id, Connection connection, boolean close){
        UserDAO userDAO = daoFactory.createUserDAO(connection);
        User user = userDAO.findById(id);
        if (user.getBalance().compareTo(total) < 0) {
            throw new NotEnoughMoneyException();
        }
        user.setBalance(user.getBalance().subtract(total));
        boolean result = userDAO.update(user);
        if (close) {
            ConnectionManager.close(connection);
        }
        return result;
    }

}

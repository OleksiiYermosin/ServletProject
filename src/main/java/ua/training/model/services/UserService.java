package ua.training.model.services;

import ua.training.model.dao.DAOFactory;
import ua.training.model.dao.UserDAO;
import ua.training.model.entities.User;

import java.sql.SQLException;

public class UserService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public boolean saveUser(User user) {
        try(UserDAO userDAO = daoFactory.createUserDAO()){
            return userDAO.create(user);
        }
    }

    public User findUserByUsername(String username) throws IllegalArgumentException{
        try(UserDAO userDAO = daoFactory.createUserDAO()){
            return userDAO.findUserByUsername(username).orElseThrow(IllegalArgumentException::new);
        }
    }

}

package ua.training.model.services;

import ua.training.model.dao.DAOFactory;
import ua.training.model.dao.UserDAO;
import ua.training.model.entities.User;

public class UserService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public void saveUser(User user){
        try(UserDAO userDAO = daoFactory.createUserDAO()){
            userDAO.create(user);
        }
    }

    public User findUserByUsername(String username){

    }

}

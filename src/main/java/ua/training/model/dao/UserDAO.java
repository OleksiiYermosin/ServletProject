package ua.training.model.dao;

import ua.training.model.entities.User;

import java.util.Optional;

public interface UserDAO extends GenericDAO<User> {

    Optional<User> findUserByUsername(String username);

}

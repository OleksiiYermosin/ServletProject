package ua.training.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> extends AutoCloseable {
    boolean create(T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
    void close();
}

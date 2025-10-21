package io.lorddead.networksystem.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<E> {
    void save(E e) throws SQLException;

    void update(E e) throws SQLException;

    List<E> getAllModels() throws SQLException;

    void delete(E e) throws SQLException;
}

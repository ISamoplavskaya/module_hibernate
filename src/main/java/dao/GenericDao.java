package dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> findByID(long id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}

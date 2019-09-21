package edu.unapec.shoppingorders.repositories;

import java.io.IOException;
import java.util.List;

public interface Repository<T, ID> {
    ID add(T entity) throws IOException;
    void update(T entity) throws IOException;
    void delete(ID id) throws IOException;
    T findById(ID id) throws IOException;
    List<T> getAll();
}

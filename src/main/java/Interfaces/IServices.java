package Interfaces;

import java.util.List;

public interface IServices<T> {
    void ajouterEntity(T t);
    void updateEntity(T t);
    void deleteEntity(T t);
    List<T> getAllData();
}


package interfaces;

import entities.Form;

import java.util.List;

public interface IServices <T>{
    void addEntity(T t);
    void updateEntity(T t);
    void deleteEntity(T t);
    List<T> getAllData();

}
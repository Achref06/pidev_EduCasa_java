package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface Itraitement<T> {
    void addT(T t) throws SQLException;
    void updateT(T t) throws SQLException;
    void deleteT(T t)throws SQLException;
    List<T> displayList()  throws SQLException;
}

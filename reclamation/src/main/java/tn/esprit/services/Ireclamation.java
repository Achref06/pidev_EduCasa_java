package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface Ireclamation<R> {
    void add(R r) throws SQLException;
    void update(R r)throws SQLException;
    void  delete(R r)throws SQLException;
    List<R> displayList()  throws SQLException;
}

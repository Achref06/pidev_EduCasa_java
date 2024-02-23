package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IServices<T>{

    void  addEntity(T t) throws SQLException;
    void  updateEntity(T t);
    void  deleteEntity(T t);
List<T> getAllData();

}

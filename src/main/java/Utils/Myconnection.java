package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnection {

    private final String url="jdbc:mysql://localhost:3306/pidev";
    private final String login="root";
    private final String mdp="";
    public static Myconnection instance;


    Connection cnx;
    private Myconnection(){
        try {
            cnx= DriverManager.getConnection(url,login,mdp);
            System.out.println("connexion établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static Myconnection getInstance() {
        if(instance==null){
            instance =new Myconnection();
        }
        return instance;
    }
}

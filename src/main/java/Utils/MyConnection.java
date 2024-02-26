package Utils;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private final  String url="jdbc:mysql://localhost:3306/pidev";
    private final  String login="root";
    private final  String mdp="";
    public static MyConnection instance;
    private Connection cnx;

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance() {
        if(instance==null){
            instance = new MyConnection();
        }
        return instance;
    }

    public MyConnection(){
        try {
            cnx = DriverManager.getConnection(url,login,mdp);
            System.out.println("Connexion etablie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}



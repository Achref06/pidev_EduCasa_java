package tn.esprit.app;

import java.time.LocalDate;
import java.util.Date;
import tn.esprit.entities.traitement;
import tn.esprit.services.reclamationService;
import tn.esprit.Utils.myDB;
import tn.esprit.entities.reclamation;
import tn.esprit.services.traitementServices;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {



        //    MyConnection mc = new MyConnection();
        reclamation r = new reclamation(1,"incident","prof", LocalDate.of(2024, 2, 15));
        reclamation r1 = new reclamation(1,"aaa","cours", LocalDate.of(2024, 2, 5));

        reclamationService rs = new reclamationService();
        try {
            rs.add(r);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            rs.update(r);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs.delete(r);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(rs .displayList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        traitement t = new traitement( 1 ,"c'est bon");
        traitementServices ts = new traitementServices();
        try {
            ts.addT(t);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*try {
            ts.updateT(t);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       /* try {
            ts.deleteT(t);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(ts .displayList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        /*myDB instance1=myDB.getInstance();
        myDB instance2=myDB.getInstance();

        reclamation r=new reclamation(17/2/2024,"****","cours");
        reclamationService rs=new reclamationService();
        reclamation r1=new reclamation(13/2/2024,"***","prof");



            rs.add(r1);
            (SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            System.out.println(rs.displayList());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            rs.update(r,17/2/2024,"***","cours");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        try{
            rs.delete(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println(rs.displayList());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }*/

    }
}
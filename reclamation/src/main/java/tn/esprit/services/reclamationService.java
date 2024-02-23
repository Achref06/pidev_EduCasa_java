package tn.esprit.services;

import tn.esprit.Utils.myDB;
import tn.esprit.entities.reclamation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;



public class  reclamationService implements Ireclamation<reclamation> {


    @Override
    public void add(reclamation r) throws SQLException {
        String requete = "INSERT INTO reclamation ( numR,description,type , date ) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst = myDB.getInstance().getCon().prepareStatement(requete);
            pst.setInt(1, reclamation.getNumR());
            pst.setString(2, reclamation.getDescription());
            pst.setString(3, reclamation.getType());
            pst.setDate(4, Date.valueOf(reclamation.getDate()));

            pst.executeUpdate();
            System.out.println("reclamation ajoutee");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(reclamation r) throws SQLException {
        String requete = "UPDATE reclamation SET description=?, type=?, date=? WHERE numR=?";
        try {
            PreparedStatement pst = myDB.getInstance().getCon().prepareStatement(requete);
            pst.setString(1, reclamation.getDescription());
            pst.setString(2, reclamation.getType());
            pst.setDate(3, Date.valueOf(reclamation.getDate()));
            pst.setInt(4, reclamation.getNumR());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("reclamation with numR " + reclamation.getNumR() + " updated successfully");
            } else {
                System.out.println("No reclamation found with ID " + reclamation.getNumR() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public  void delete(reclamation reclamation) throws SQLException {
        String requete = "DELETE FROM reclamation WHERE numR=?";
        try {
            PreparedStatement pst = myDB.getInstance().getCon().prepareStatement(requete);
            pst.setInt(1, reclamation.getNumR());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("reclamation with numR " + reclamation.getNumR() + " deleted successfully");
            } else {
                System.out.println("No reclamation found with numR " + reclamation.getNumR());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


   /* @Override
    public List<reclamation> displayList() throws SQLException {
        List<reclamation> rec = new ArrayList<>();
        String requete = "SELECT * FROM reclamation";
        try {
            Statement st = myDB.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                reclamation r = new reclamation(rs.getInt("numR"),
                r.setNumR(rs.getInt(1))
                r.setDescription(rs.getString("description"))
                r.setType(rs.getString("type"))
                r.setDate(rs.getDate("date"))
                );

                rec.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rec;
    }*/


    @Override
    public List<reclamation> displayList() throws SQLException {
        System.out.println("reclama");
        List<reclamation> rec = new ArrayList<>();
        String requete = "SELECT * FROM reclamation";
        try {
            Statement st = myDB.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                reclamation r = new reclamation(rs.getInt("numR"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getDate("date").toLocalDate()
                );

                rec.add(r);

            }
            return rec;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return rec;
    }


}



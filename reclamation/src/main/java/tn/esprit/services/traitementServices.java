package tn.esprit.services;
import tn.esprit.Utils.myDB;

import tn.esprit.entities.reclamation;
import tn.esprit.entities.traitement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class traitementServices implements Itraitement<traitement>{
    @Override
    public void addT(traitement traitement) throws SQLException {
        String requete = "INSERT INTO traitement ( idT, numR ,reponse ) VALUES (?,?,?)";
        try {
            PreparedStatement pst = myDB.getInstance().getCon().prepareStatement(requete);
            pst.setInt(1, traitement.getIdT());
            pst.setInt(2, traitement.getNumR());
            pst.setString(3, traitement.getReponse());

            pst.executeUpdate();
            System.out.println("traitement ajoutee");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void updateT(traitement traitement) throws SQLException {
        String requete = "UPDATE traitement SET  numR=?,reponse=?  WHERE idT=?";
        try {
            PreparedStatement pst = myDB.getInstance().getCon().prepareStatement(requete);
            pst.setInt(1, traitement.getIdT());
            pst.setInt(2, traitement.getNumR());
            pst.setString(3, traitement.getReponse());


            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("traitement with idT " + traitement.getIdT() + " updated successfully");
            } else {
                System.out.println("No traitement found with idT " + traitement.getIdT() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }



    @Override
    public void deleteT(traitement traitement) throws SQLException {
        String requete = "DELETE FROM traitement WHERE idT=?";
        try {
            PreparedStatement pst = myDB.getInstance().getCon().prepareStatement(requete);
            pst.setInt(1, traitement.getIdT());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("traitement with idT " + traitement.getIdT() + " deleted successfully");
            } else {
                System.out.println("No traitement found with idT " + traitement.getIdT());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<traitement> displayList() throws SQLException {
        List<traitement> tlist = new ArrayList<>();
        String requete = "SELECT * FROM traitement";
        try {
            Statement st = myDB.getInstance().getCon().createStatement();
            ResultSet ts = st.executeQuery(requete);
            while (ts.next()) {
                traitement t = new traitement();
                t.setIdT(ts.getInt(1));
                t.setNumR(ts.getInt(2));
                t.setReponse(ts.getString("reponse"));

                tlist.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tlist;

    }
}

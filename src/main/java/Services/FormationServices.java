package Services;

import Entities.Formation;
import Interfaces.IServices;
import Utils.Myconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FormationServices implements IServices<Formation> {

    @Override
    public void ajouterEntity(Formation formation) {
        String requete="INSERT INTO formation (idFormation,nomFormation,dureeFormation,niveauFormation,nbJours) " +
                "VALUES  ('"+formation.getIdFormation()+"','"+formation.getNomFormation()+"','"+formation.getDureeFormation()+"','"+formation.getNiveauFormation()+"','"+formation.getNbJours()+"')";
        try {
            Statement st = Myconnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("formation ajoutée!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEntity(Formation formation) {
        String requete = "UPDATE formation SET nomFormation=?, niveauFormation=? WHERE idFormation=?";
        try {
            PreparedStatement pst = Myconnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, formation.getNomFormation());
            pst.setString(2, formation.getNiveauFormation());
            pst.setInt(3, formation.getIdFormation());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Formation with ID " + formation.getIdFormation() + " updated successfully");
            } else {
                System.out.println("No Formation found with ID " + formation.getIdFormation() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Formation formation) {
        String requete ="DELETE FROM formation where nomFormation=?";
        try {
            PreparedStatement pst= Myconnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,formation.getNomFormation());
            pst.executeUpdate();
            System.out.println("formation supprimee!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Formation> getAllData() {
            ObservableList<Formation> formations = FXCollections.observableArrayList();
            String requete = "SELECT * FROM formation";
            try {
                PreparedStatement pst = Myconnection.getInstance().getCnx().prepareStatement(requete);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    formations.add(new Formation(
                            rs.getInt("idFormation"),
                            rs.getString("nomFormation"),
                            rs.getString("dureeFormation"),
                            rs.getString("niveauFormation"),
                            rs.getInt("nbJours")

                    ));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return formations;
        }
}

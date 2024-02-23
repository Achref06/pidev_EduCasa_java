package services;

import entities.Offre;
import interfaces.IServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OffreServices implements IServices<Offre> {
        @Override
        public void addEntity(Offre offre) throws SQLException {
            String requete = " INSERT INTO offre (nom,prix,statut,description) VALUES ('"+offre.getNom()+"','"+offre.getDescription()+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("Personne ajoutée");
        }
        public void addEntity2(Offre offre){
            String requete = "INSERT INTO offre (nom,prix,statut,description) VALUES(?,?,?,?)";
            try {
                PreparedStatement pst =   MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setString(1,offre.getNom());
                pst.setFloat(2,offre.getPrix());
                pst.setString(3,offre.getStatut());
                pst.setString(4,offre.getDescription());

                pst.executeUpdate();
                System.out.println("Offre added ");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void updateEntity(Offre offre) {

                String requete = "UPDATE offre SET nom=?, prix=?,statut=?, description=?  WHERE id=?";
                try {
                    PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                    pst.setInt(5,offre.getId());
                    pst.setString(1, offre.getNom());
                    pst.setFloat(2, offre.getPrix());
                    pst.setString(3, offre.getStatut());
                    pst.setString(4, offre.getDescription());



                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Offre with ID " + offre.getId() + " updated successfully");
                    } else {
                        System.out.println("No Offre found with ID " + offre.getId() + " for updating");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        @Override
        public void deleteEntity(Offre offre) {

                String requete = "DELETE FROM offre WHERE id=?";
                try {
                    PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                    pst.setInt(1, offre.getId());
                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Offre with ID " + offre.getId() + " deleted successfully");
                    } else {
                        System.out.println("No Offre found with ID " + offre.getId());
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }


        @Override
        public ObservableList<Offre> getAllData() {

            ObservableList<Offre> data = FXCollections.observableArrayList() ;
            String requete = "SELECT * FROM offre";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs =  st.executeQuery(requete);
                while(rs.next()){
                    Offre offre = new Offre();
                    offre.setId(rs.getInt(1));
                    offre.setNom(rs.getString("nom"));
                    offre.setPrix(rs.getFloat("prix"));
                    offre.setStatut(rs.getString("statut"));
                    offre.setDescription(rs.getString("description"));


                    data.add(offre);
                }
            }
            catch (SQLException e){
                System.out.println();
            }
            return data;


        }
    }


package Services;

import Entities.Categorie;
import Entities.Cours;
import Interfaces.IServices;
import Utils.Myconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorieServices implements IServices<Categorie> {
    @Override
    public void ajouterEntity(Categorie categorie) {
        String requete="INSERT INTO categorie (nomCategorie) " +
                "VALUES  ('"+categorie.getNomCategorie()+"')";
        try {
            Statement st = Myconnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("categorie ajoutée!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEntity(Categorie categorie) {
        String requete = "UPDATE categorie SET nomCategorie=? WHERE idCategorie=?";
        try {
            PreparedStatement pst = Myconnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, categorie.getNomCategorie());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Categorie with ID " + categorie.getIdCategorie()+ " updated successfully");
            } else {
                System.out.println("No Categorie found with ID " + categorie.getNomCategorie() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Categorie categorie) {
        String requete ="DELETE FROM categorie where nomCategorie=?<";
        try {
            PreparedStatement pst= Myconnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,categorie.getNomCategorie());
            pst.executeUpdate();
            System.out.println("Categorie supprimee!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Categorie> getAllData() {
        List<Categorie> data=new ArrayList<>();
        String requete="SELECT * FROM categorie";
        try {
            Statement st= Myconnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Categorie p=new Categorie();
                p.setIdCategorie(rs.getInt(1));
                p.setNomCategorie(rs.getString("nomCategorie"));
                data.add(p);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return data;
    }
}

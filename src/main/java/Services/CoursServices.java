package Services;

import Entities.Cours;
import Interfaces.IServices;
import Utils.Myconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoursServices implements IServices<Cours> {
    @Override
    public void ajouterEntity(Cours cours) {
        String requete="INSERT INTO cours (nomCour) " +
                "VALUES  ('"+cours.getNomCour()+"')";
        try {
            Statement st = Myconnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("cours ajoutée!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEntity(Cours cours) {
        String requete = "UPDATE cours SET nomCour=? WHERE idCour=?";
        try {
            PreparedStatement pst = Myconnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, cours.getNomCour());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cour with ID " + cours.getIdCour() + " updated successfully");
            } else {
                System.out.println("No Cour found with ID " + cours.getNomCour() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Cours cours) {
        String requete ="DELETE FROM cours where nomCour=?<";
        try {
            PreparedStatement pst= Myconnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,cours.getNomCour());
            pst.executeUpdate();
            System.out.println("cours supprimee!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cours> getAllData() {
        List<Cours> data=new ArrayList<>();
        String requete="SELECT * FROM cours";
        try {
            Statement st= Myconnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Cours p=new Cours();
                p.setIdCour(rs.getInt(1));
                p.setNomCour(rs.getString("nomCour"));
                data.add(p);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return data;
    }
    }


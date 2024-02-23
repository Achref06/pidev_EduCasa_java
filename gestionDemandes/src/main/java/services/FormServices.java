package services;

import entities.Form;
import interfaces.IServices;
import utils.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FormServices implements IServices<Form>{

    @Override
    public void addEntity(Form form) {
        String requete = "INSERT INTO formulaire (date, statut) VALUES (?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setDate(1, Date.valueOf(form.getDate()));
            pst.setString(2, form.getStatut());
            pst.executeUpdate();
            System.out.println("Formulaire added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(Form form) {
        String requete = "UPDATE formulaire SET date=?, statut=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setDate(1, Date.valueOf(form.getDate()));
            pst.setString(2, form.getStatut());
            pst.setInt(3, form.getId());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Formulaire with ID " + form.getId() + " updated successfully");
            } else {
                System.out.println("No Formulaire found with ID " + form.getId() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Form form) {
        String requete = "DELETE FROM formulaire WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, form.getId());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Formulaire with ID " + form.getId() + " deleted successfully");
            } else {
                System.out.println("No Formulaire found with ID " + form.getId());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Form> getAllData() {
        List<Form> data = new ArrayList<>();
        String requete = "SELECT * FROM formulaire";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Form f = new Form();
                f.setId(rs.getInt(1));
                f.setDate(LocalDate.parse(rs.getString("date")));
                f.setStatut(rs.getString("statut"));
                data.add(f);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}

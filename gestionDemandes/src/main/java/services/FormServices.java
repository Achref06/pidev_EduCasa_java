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
    public List<Form> searchForm(String searchText) {
        List<Form> searchResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            String query = "SELECT * FROM formulaire WHERE id LIKE ? OR date LIKE ? OR statut LIKE ?";
            statement = connection.prepareStatement(query);
            String searchPattern = "%" + searchText + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dateStr = resultSet.getString("date");
                String statut = resultSet.getString("statut");
                // Create a Form object and add it to the search results list
                LocalDate date = LocalDate.parse(dateStr);
                Form form = new Form(id, date, statut);
                searchResults.add(form);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return searchResults;
    }

    public List<Form> getAllDataSortedByStatut() {
        List<Form> forms = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            String query = "SELECT * FROM formulaire ORDER BY statut";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dateForm = resultSet.getString("date");
                String statut = resultSet.getString("statut");
                // Create a Form object and add it to the list
                LocalDate date = LocalDate.parse(dateForm);
                Form form = new Form(id, date, statut);
                forms.add(form);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return forms;
    }

}

package services;

import entities.Infos;
import interfaces.IServices;
import utils.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InfosServices implements IServices<Infos> {
    @Override
    public void addEntity(Infos infos) {
        String requete = "INSERT INTO donnees (idF, nom, prenom, dateN, email, experience, motivation, matiere, diplome) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, infos.getIdF());
            pst.setString(2, infos.getNom());
            pst.setString(3, infos.getPrenom());
            pst.setDate(4, Date.valueOf(infos.getDateN()));
            pst.setString(5, infos.getEmail());
            pst.setString(6, infos.getExperience());
            pst.setString(7, infos.getMotivation());
            pst.setString(8, infos.getMatiere());
            pst.setString(9, infos.getDiplome());
            pst.executeUpdate();
            System.out.println("Données added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(Infos infos) {
        String requete = "UPDATE donnees SET idF=?, nom=?, prenom=?, dateN=?, email=?, experience=?, motivation=?, matiere=?, diplome=? WHERE idP=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, infos.getIdF());
            pst.setString(2, infos.getNom());
            pst.setString(3, infos.getPrenom());
            pst.setDate(4, Date.valueOf(infos.getDateN()));
            pst.setString(5, infos.getEmail());
            pst.setString(6, infos.getExperience());
            pst.setString(7, infos.getMotivation());
            pst.setString(8, infos.getMatiere());
            pst.setString(9, infos.getDiplome());
            pst.setInt(10, infos.getIdP());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Donnees with ID " + infos.getIdP() + " updated successfully");
            } else {
                System.out.println("No Donnees found with ID " + infos.getIdP() + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Infos infos) {
        String requete = "DELETE FROM donnees WHERE idP=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, infos.getIdP());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Donnees with ID " + infos.getIdP() + " deleted successfully");
            } else {
                System.out.println("No Donnees found with ID " + infos.getIdP());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Infos> getAllData() {
        List<Infos> dataI = new ArrayList<>();
        String requete = "SELECT * FROM donnees";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Infos i = new Infos();
                i.setIdP(rs.getInt(1));
                i.setIdF(rs.getInt(2));
                i.setNom(rs.getString("nom"));
                i.setPrenom(rs.getString("prenom"));
                i.setDateN(LocalDate.parse(rs.getString("dateN")));
                i.setEmail(rs.getString("email"));
                i.setExperience(rs.getString("experience"));
                i.setMotivation(rs.getString("motivation"));
                i.setMatiere(rs.getString("matiere"));
                i.setDiplome(rs.getString("diplome"));
                dataI.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataI;
    }

    public int getLastInsertedId() {
        int lastId = -1; // Default value if no ID is found or an error occurs

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String sql = "SELECT MAX(id) AS last_id FROM formulaire";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lastId = resultSet.getInt("last_id");
                }
            }
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace(); // Handle exception properly in your application
        }

        return lastId;
    }

    public int getNextId() {
        int nextId = -1; // Default value if no next ID is found or an error occurs

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String sql = "SELECT MAX(idP) + 1 AS next_id FROM donnees";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nextId = resultSet.getInt("next_id");
                }
            }
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace(); // Handle exception properly in your application
        }

        return nextId;
    }

    public List<Infos> searchInfos(String searchText) {
        List<Infos> searchResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            String query = "SELECT * FROM donnees WHERE idP LIKE ? OR idF LIKE ? OR nom LIKE ? OR prenom LIKE ? OR email LIKE ? OR matiere LIKE ?";
            statement = connection.prepareStatement(query);
            String searchPattern = "%" + searchText + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            statement.setString(4, searchPattern);
            statement.setString(5, searchPattern);
            statement.setString(6, searchPattern);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idP = resultSet.getInt("idP");
                int idF = resultSet.getInt("idF");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String matiere = resultSet.getString("matiere");
                Infos infos = new Infos(idP, idF, nom, prenom, email, matiere);
                searchResults.add(infos);
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

    public List<Infos> getAllDataSortedByMatiere() {
        List<Infos> infos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            String query = "SELECT * FROM donnees ORDER BY matiere";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idP = resultSet.getInt("idP");
                int idF = resultSet.getInt("idF");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String matiere = resultSet.getString("matiere");
                Infos info = new Infos(idP, idF, nom, prenom, email, matiere);
                infos.add(info);
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

        return infos;
    }

    public List<Infos> getDataProf(String searchText) {
        List<Infos> searchResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            String query = "SELECT nom, prenom, matiere FROM donnees WHERE nom LIKE ? OR prenom LIKE ? OR matiere LIKE ?";
            statement = connection.prepareStatement(query);
            String searchPattern = "%" + searchText + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String matiere = resultSet.getString("matiere");
                // Create an Infos object and add it to the search results list
                Infos infos = new Infos(nom, prenom, matiere);
                searchResults.add(infos);
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


    public List<Infos> getDataProf() {
        List<Infos> dataProf = new ArrayList<>();
        String requete = "SELECT nom , prenom , matiere FROM donnees";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Infos i = new Infos();
                i.setNom(rs.getString("nom"));
                i.setPrenom(rs.getString("prenom"));
                i.setMatiere(rs.getString("matiere"));
                dataProf.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataProf;
    }
}

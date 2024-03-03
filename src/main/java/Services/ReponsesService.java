package Services;

import Entities.Reponses;
import Interfaces.IServices;
import Utils.MyConnection;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReponsesService implements IServices <Reponses> {

    public ReponsesService(Connection cnx) {
    }

    private boolean questionExists(int idQuestion) {
        try {
            String sql = "SELECT id FROM question WHERE id = ?";
            try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(sql)) {
                statement.setInt(1, idQuestion);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Retourne true si l'idQuestion existe, false sinon
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
            return false; // En cas d'erreur, considérez que l'idQuestion n'existe pas
        }
    }
    @Override
    public void addEntity(Reponses reponses) {
        if(questionExists(reponses.getIdq())) {
            String requete = "INSERT INTO reponse (idq,rep,statut) VALUES ( ?,?,? )";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, reponses.getIdq());
                pst.setString(2, reponses.getRep());
                pst.setBoolean(3, reponses.isStatut());
                pst.executeUpdate();
                System.out.println("reponse ajoute!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Question inexistante");
        }

    }




    @Override
    public void updateEntity(Reponses reponses) {

    }

    @Override
    public void deleteEntity(Reponses reponses) {

    }

    @Override
    public ObservableList<Reponses> getAllData() {
        return null;
    }

    public List<Reponses> getAllReponses() {
        List<Reponses> reponsesList = new ArrayList<>();

        String requete = "SELECT * FROM reponses";  // Remplacez cela par votre propre requête SQL
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reponses reponse = new Reponses();
                // Complétez ici la création de l'objet Reponses à partir des données de la base de données
                reponse.setId(rs.getInt("id"));
                reponse.setStatut(rs.getBoolean("statut"));
                // Ajoutez d'autres attributs selon votre modèle de données

                reponsesList.add(reponse);
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez les exceptions de base de données selon les besoins
        }

        return reponsesList;
    }
}

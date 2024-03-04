package Services;

import Entities.Quiz;
import Interfaces.IServices;
import Utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizServices implements IServices<Quiz> {


    @Override
    public void addEntity(Quiz quiz) {
        String requete= "INSERT INTO Quiz (nom,nbQuest,note) VALUES ( ?,?,? )";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,quiz.getNom());
            pst.setInt(2,quiz.getNbQuest());
            pst.setInt(3,quiz.getNote());
            pst.executeUpdate();
            System.out.println("Quiz ajoute!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(Quiz quiz) {
        String requete="UPDATE quiz SET nom=?, nbQuest=?, note=? WHERE id=?";
        try {
            PreparedStatement pst=MyConnection.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1,quiz.getNom());
            pst.setInt(2,quiz.getNbQuest());
            pst.setInt(3,quiz.getNote());
            pst.setInt(4,quiz.getId());
            int rowsAffected=pst.executeUpdate();
            if (rowsAffected>0){
                System.out.println("quiz with id:"+quiz.getId()+"updated successfully");}
                else{
                    System.out.println("No quiz found");
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Quiz quiz) {
        String requete="DELETE FROM quiz WHERE id=?";
        try {
            PreparedStatement pst=MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1,quiz.getId());
            int rowsAffected=pst.executeUpdate();
            if (rowsAffected>0){
                System.out.println("quiz with ID"+quiz.getId()+"supprime");
            }
            else{
                System.out.println("quiz non supprime");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Quiz> getAllData() {
        ObservableList<Quiz> data = FXCollections.observableArrayList() ;
        String requete = "SELECT * FROM quiz";
        try {
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while (rs.next()){
                Quiz q=new Quiz();
                q.setId(rs.getInt(1));
                q.setNom(rs.getString("nom"));
                q.setNbQuest(rs.getInt("nbQuest"));
                q.setNote(rs.getInt("note"));
                data.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;

    }
    public Quiz getQuizById(int quizId) {
        String requete = "SELECT * FROM quiz WHERE id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, quizId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt("id"));
                quiz.setNbQuest(rs.getInt("nbQuest"));
                // Ajoutez d'autres attributs si nécessaire

                return quiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retourne null si le quiz n'est pas trouvé
    }

    public Quiz getNomQuizById(int quizId) {

        Quiz quiz = null;
        String query = "SELECT id, nom, nbQuest FROM quiz WHERE id = ?";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            preparedStatement.setInt(1, quizId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    int nbQuest = resultSet.getInt("nbQuest");

                    quiz = new Quiz(id, nom, nbQuest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'exception correctement
        }

        return quiz;
    }
}

package Services;

import Entities.Avancement;
import Interfaces.IServices;
import Utils.MyConnection;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvancementService implements IServices<Avancement> {


    public Avancement getAvancementByStudentAndQuestion( int questionId, int quizId) {
        String requete="SELECT * FROM avancement WHERE idquestion = ? AND idquiz = ?";
        Avancement avancement=null;
        try{
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, questionId);
            pst.setInt(2, quizId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Si une ligne est retournée, créez un objet Avancement avec les données de la base de données
                avancement = new Avancement();
                avancement.setId(rs.getInt("id"));
                avancement.setIdquestion(rs.getInt("idquestion"));
                avancement.setIdquiz(rs.getInt("idquiz"));
                avancement.setNoteQuiz(rs.getInt("noteQuiz"));
                // Ajoutez d'autres attributs selon votre modèle de données
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return avancement;
    }


    public void AvancementServices(Connection cnx) {
    }

    @Override
    public void addEntity(Avancement avancement) {
        String requete = "INSERT INTO avancement (idquestion, idquiz, noteQuiz) VALUES (?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete)) {
            pst.setInt(1,avancement.getIdquestion());
            pst.setInt(2,avancement.getIdquiz());
            pst.setInt(3,avancement.getNoteQuiz());
            pst.executeUpdate();
            System.out.println("Avancement ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(Avancement avancement) {

    }

    @Override
    public void deleteEntity(Avancement avancement) {

    }



    @Override
    public ObservableList<Avancement> getAllData() {
        return null;
    }
}


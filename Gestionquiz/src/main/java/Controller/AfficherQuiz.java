package Controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Entities.Quiz;
import Services.QuizServices;
import Utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class AfficherQuiz implements Initializable {
    int index,idd;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> nbQuest;

    @FXML
    private TableColumn<?, ?> nom;

    @FXML
    private TableColumn<?, ?> note;


    @FXML
    private TextField nbQuestU;


    @FXML
    private TextField nomU;


    @FXML
    private TextField noteU;
    @FXML
    private TableView<Quiz> table;


@Override
    public void initialize(URL url, ResourceBundle resources) {
        afficherQuiz();
    }

    public void afficherQuiz(){
        QuizServices quizServices = new QuizServices();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nbQuest.setCellValueFactory(new PropertyValueFactory<>("nbQuest"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        table.setItems(quizServices.getAllData());
    }

    @FXML
    void modifier(ActionEvent event) {
        String nom;
        int nbQuest, note;
        index= table.getSelectionModel().getSelectedIndex();
        idd = Integer.parseInt(String.valueOf(table.getItems().get(index).getId()));
        nom=nomU.getText();
        nbQuest=Integer.valueOf(nbQuestU.getText());
        note=Integer.valueOf(noteU.getText());

            String requete = "UPDATE quiz SET nom=?, nbQuest=?,note=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, nom);
            pst.setInt(2,nbQuest);
            pst.setInt(3, note);
            pst.setInt(4,idd);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            int rowsAffected = pst.executeUpdate();
            afficherQuiz();

            if (rowsAffected > 0) {
                System.out.println("Quiz with ID " + idd + " updated successfully");
            } else {
                System.out.println("No Quiz found with ID " + idd + " for updating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        Quiz clickedquiz =  table.getSelectionModel().getSelectedItem();
        table.getItems().remove(clickedquiz);
        QuizServices quizServices = new QuizServices();
        quizServices.deleteEntity(clickedquiz);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le quiz a été supprimé avec succés");
        alert.show();

    }
    @FXML
    void quiz(MouseEvent event) {
        Quiz clickedquiz =  table.getSelectionModel().getSelectedItem();
        nomU.setText(clickedquiz.getNom());
        nbQuestU.setText(String.valueOf(clickedquiz.getNbQuest()));
        noteU.setText(String.valueOf(clickedquiz.getNote()));
    }
}

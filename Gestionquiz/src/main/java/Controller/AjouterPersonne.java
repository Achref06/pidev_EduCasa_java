package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Quiz;
import Services.QuizServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class AjouterPersonne {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nbQuest;

    @FXML
    private TextField nom;

    @FXML
    private TextField note;

    @FXML
    void ajout(ActionEvent event) {
        Quiz quiz = new Quiz(nom.getText(),Integer.valueOf(nbQuest.getText()),Integer.valueOf(note.getText()));
        QuizServices quizServices = new QuizServices();
        quizServices.addEntity(quiz);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Quiz ajouté!");
        alert.show();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherQuiz.fxml"));
        try {
            Parent root=loader.load();
            AfficherQuiz afficherQuiz=loader.getController();
         //   afficherQuiz.setNom(nom.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void initialize() {

    }

}

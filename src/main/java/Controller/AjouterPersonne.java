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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
       try {
           int nbQuestions = Integer.parseInt(nbQuest.getText());
           int noteQuiz = Integer.parseInt(note.getText());
           if(noteQuiz > nbQuestions){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("La note ne peut pas être supérieure au nombre de questions.");
               alert.show();
           }
           else{
               Quiz quiz = new Quiz(nom.getText(), Integer.valueOf(nbQuest.getText()), Integer.valueOf(note.getText()));
               QuizServices quizServices = new QuizServices();
               quizServices.addEntity(quiz);
               createAccountForm();
           }




       }catch (NumberFormatException e){
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Veuillez entrer des nombres valides pour le nb de questions et la note");
           alert.show();
       }
    }
    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherQuiz.fxml"));
            Stage registerStage = new Stage();

            registerStage.setScene(new Scene(root));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
//Alert alert = new Alert(Alert.AlertType.INFORMATION);
// alert.setContentText("Quiz ajouté!");
//  alert.show();
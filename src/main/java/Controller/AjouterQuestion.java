package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Questions;
import Entities.Quiz;
import Entities.Reponses;
import Services.QuestionsServices;
import Services.QuizServices;
import Utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AjouterQuestion {

    @FXML
    private Label nomQuiz;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button confirmer;
    @FXML
    private Label questRes;

    @FXML
    private TextField idq;

    @FXML
    private TextField quest;

    @FXML
    private TextField rep1;

    @FXML
    private TextField rep2;

    @FXML
    private TextField rep3;

    @FXML
    private TextField rep4;

    @FXML
    private ChoiceBox<Boolean> statut1;

    @FXML
    private ChoiceBox<Boolean> statut2;

    @FXML
    private ChoiceBox<Boolean> statut3;

    @FXML
    private ChoiceBox<Boolean> statut4;

    @FXML
    private Button listeQuiz;

    private void updateNomQuizLabel(String quizId) {
        try {
            // Convertir l'ID du quiz en entier
            int intQuizId = Integer.parseInt(quizId);

            // Obtenez le nom du quiz en fonction de l'ID
            QuizServices quizService = new QuizServices();
            Quiz quiz = quizService.getNomQuizById(intQuizId);

            if (quiz != null) {
                // Mettez à jour le label avec le nom du quiz
                nomQuiz.setText( "nom quiz: "+quiz.getNom());
            } else {
                // Si le quiz n'est pas trouvé, effacez le label
                nomQuiz.setText("");
            }
        } catch (NumberFormatException e) {
            // Gérer l'exception si l'ID du quiz n'est pas un entier
            nomQuiz.setText("");
        }
    }





    @FXML
    void listeQuiz(ActionEvent event) {
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
    void confirmer(ActionEvent event) {
        if(idq.getText().isEmpty()||quest.getText().isEmpty()||rep1.getText().isEmpty()||rep2.getText().isEmpty()||rep3.getText().isEmpty()||rep4.getText().isEmpty()){
            showAlert("Veuillez remplir les champs");
        }

        Questions nouvelleQuestion = new Questions();
        QuestionsServices questionsServices= new QuestionsServices(MyConnection.getInstance().getCnx());

        nouvelleQuestion.setIdquiz(Integer.parseInt(idq.getText()));
        nouvelleQuestion.setQuest(quest.getText());
        if (rep1.getText().equals(rep2.getText()) || rep1.getText().equals(rep3.getText()) || rep1.getText().equals(rep4.getText())
                || rep2.getText().equals(rep3.getText()) || rep2.getText().equals(rep4.getText()) || rep3.getText().equals(rep4.getText())) {
            // Affiche une alerte ou effectue une autre action si les réponses ne sont pas différentes
            showAlert("Les réponses doivent être différentes.");
            return; // Sort de la méthode sans ajouter la question
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Ajout d'une question");
        confirmationAlert.setContentText("Voulez-vous vraiment ajouter cette question ?");

        ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(ouiButton, nonButton);


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ouiButton){
            List<Reponses> listeRep = new ArrayList<>();
            listeRep.add(new Reponses(rep1.getText(), statut1.getValue()));
            listeRep.add(new Reponses(rep2.getText(), statut2.getValue()));
            listeRep.add(new Reponses(rep3.getText(), statut3.getValue()));
            listeRep.add(new Reponses(rep4.getText(), statut4.getValue()));

            nouvelleQuestion.setListeRep(listeRep);

//METIER1

                int quizId=Integer.parseInt(idq.getText());
                int nbQuestionsAjoutees=questionsServices.countQuestionsByQuizId(quizId);
                int nbQuestionsRestantes=obtenirNbTotalQuestions()-nbQuestionsAjoutees-1;


            showAlert("Questions restantes: "+ nbQuestionsRestantes);

            questionsServices.addEntity(nouvelleQuestion);

            idq.clear();
            quest.clear();
            rep1.clear();
            rep2.clear();
            rep3.clear();
            rep4.clear();
            statut1.setValue(false);
            statut2.setValue(false);
            statut3.setValue(false);
            statut4.setValue(false);
        }

    }

    @FXML
    void initialize() {


        statut1.getItems().addAll(true, false);
        statut2.getItems().addAll(true, false);
        statut3.getItems().addAll(true, false);
        statut4.getItems().addAll(true, false);

        idq.textProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour le label avec le nom du quiz
            updateNomQuizLabel(newValue);
        });
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private int obtenirNbTotalQuestions() {
        int quizId = Integer.parseInt(idq.getText());

        QuizServices quizService = new QuizServices();

        // Remplacez quizId par l'ID du quiz que vous utilisez
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz != null) {
            return quiz.getNbQuest();
        } else {
            // Gérer le cas où le quiz n'est pas trouvé
            return 0;
        }
    }



}

package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Questions;
import Entities.Reponses;
import Services.QuestionsServices;
import Utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AjouterQuestion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button confirmer;

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
    void confirmer(ActionEvent event) {

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

        List<Reponses> listeRep = new ArrayList<>();
        listeRep.add(new Reponses(rep1.getText(), statut1.getValue()));
        listeRep.add(new Reponses(rep2.getText(), statut2.getValue()));
        listeRep.add(new Reponses(rep3.getText(), statut3.getValue()));
        listeRep.add(new Reponses(rep4.getText(), statut4.getValue()));

        nouvelleQuestion.setListeRep(listeRep);


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

    @FXML
    void initialize() {

        statut1.getItems().addAll(true, false);
        statut2.getItems().addAll(true, false);
        statut3.getItems().addAll(true, false);
        statut4.getItems().addAll(true, false);
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

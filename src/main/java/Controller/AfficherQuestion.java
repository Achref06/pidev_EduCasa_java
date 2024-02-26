package Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Questions;
import Entities.Reponses;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;

public class AfficherQuestion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea quest;

    @FXML
    private TextField rep1;

    @FXML
    private TextField rep2;

    @FXML
    private TextField rep3;

    @FXML
    private TextField rep4;
    @FXML
    private Button confirmer;
    private List<Questions> listeQuestions;
    private int indexQuestionActuelle;



    @FXML
    void confirmer(ActionEvent event) {
        System.out.println("Confirm button clicked...");
        questionSuivante();
    }

    @FXML
    void initialize() {

        if (listeQuestions != null && !listeQuestions.isEmpty()) {
            indexQuestionActuelle = 0;
            afficherQuestionActuelle();
        }
    }

    private void afficherQuestionActuelle() {

        Questions questionActuelle = listeQuestions.get(indexQuestionActuelle);
        System.out.println("Afficher la question actuelle");

            quest.setText(questionActuelle.getQuest());
        quest.setMouseTransparent(true);
        quest.setEditable(false);
            List<Reponses> reponses = questionActuelle.getListeRep();
            if (reponses.size() >= 4) {
                rep1.setText(reponses.get(0).getRep());
                rep2.setText(reponses.get(1).getRep());
                rep3.setText(reponses.get(2).getRep());
                rep4.setText(reponses.get(3).getRep());


                rep1.setMouseTransparent(true);
                rep1.setEditable(false);
                rep2.setMouseTransparent(true);
                rep2.setEditable(false);
                rep3.setMouseTransparent(true);
                rep3.setEditable(false);
                rep4.setMouseTransparent(true);
                rep4.setEditable(false);
            } else if (reponses.size() >= 2) {
                rep1.setText(reponses.get(0).getRep());
                rep2.setText(reponses.get(1).getRep());
                rep3.setText("");
                rep4.setText("");
                rep1.setMouseTransparent(true);
                rep1.setEditable(false);
                rep2.setMouseTransparent(true);
                rep2.setEditable(false);
                rep3.setMouseTransparent(true);
                rep3.setEditable(false);
                rep4.setMouseTransparent(true);
                rep4.setEditable(false);

            }

    }

    // Méthode pour passer à la question suivante
    public void questionSuivante() {
        if (indexQuestionActuelle < listeQuestions.size() - 1) {
            indexQuestionActuelle++;
            System.out.println("Displaying next question...");
            afficherQuestionActuelle();
        }
    }

    // Méthode pour passer à la question précédente
    private void questionPrecedente() {
        if (indexQuestionActuelle > 0) {
            indexQuestionActuelle--;
            afficherQuestionActuelle();
        }
    }

    // Méthode pour définir la liste de questions à afficher
    public void setListeQuestions(List<Questions> listeQuestions) {
        this.listeQuestions = listeQuestions;
    }

}

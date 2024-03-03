package Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Avancement;
import Entities.Questions;
import Entities.Reponses;
import Services.AvancementService;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
    private RadioButton radioButton1;

    @FXML
    private RadioButton radioButton2;

    @FXML
    private RadioButton radioButton3;

    @FXML
    private RadioButton radioButton4;

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
    private String selectedAnswer;
    @FXML
    void selectAnswer(ActionEvent event) {
        RadioButton selectedRadioButton = null;
        if (radioButton1.isSelected()) {
            selectedRadioButton = radioButton1;
        } else if (radioButton2.isSelected()) {
            selectedRadioButton = radioButton2;
        } else if (radioButton3.isSelected()) {
            selectedRadioButton = radioButton3;
        } else if (radioButton4.isSelected()) {
            selectedRadioButton = radioButton4;
        }

        // Mettre à jour le TextField avec la réponse sélectionnée
        if (selectedRadioButton != null) {
            String answerText = selectedRadioButton.getText();
            selectedAnswer = answerText;
        }
    }

    @FXML
    void confirmer(ActionEvent event) {
        System.out.println("Confirm button clicked...");

        int selectedAnswerIndex = Integer.parseInt(selectedAnswer);
        Questions questionActuelle = listeQuestions.get(selectedAnswerIndex);
int questionId=questionActuelle.getId();
int quizId=questionActuelle.getIdquiz();
        AvancementService avancementService=new AvancementService();
        Avancement avancement = avancementService.getAvancementByStudentAndQuestion(questionId, quizId);
        if (selectedAnswer != null) {
            for (Reponses reponse : questionActuelle.getListeRep()) {
                if (reponse.isSelected() && reponse.isStatut()) {
                    // 4. Si la réponse sélectionnée a un statut true, incrémentez noteQuiz
                    avancement.setNoteQuiz(avancement.getNoteQuiz() + 1);
                }
            }
        }

        // 5. Mettez à jour l'objet Avancement dans la base de données (adapté à votre implémentation)
        avancementService.updateEntity(avancement);

        questionSuivante();
        System.out.println("User selected: " + selectedAnswer);
    }










  /*  private void choisirReponse(String reponseChoisie) {
        Questions questionActuelle = listeQuestions.get(indexQuestionActuelle);
        // Mettez à jour l'objet Questions avec la réponse choisie
        questionActuelle.setReponseChoisie(reponseChoisie);
        // Vous pouvez également faire d'autres choses, par exemple, afficher un message ou passer à la question suivante.
    }*/
    @FXML
    void initialize() {

        if (listeQuestions != null && !listeQuestions.isEmpty()) {
            // Filter questions based on the quiz ID
          indexQuestionActuelle=0;
           afficherQuestionActuelle();

        }
    }
    private void afficherQuestionActuelle() {

        Questions questionActuelle = listeQuestions.get(indexQuestionActuelle);

        quest.setText(questionActuelle.getQuest());

        List<Reponses> reponses = questionActuelle.getListeRep();
        RadioButton[] radioButtons = {radioButton1, radioButton2, radioButton3, radioButton4};

        for (int i = 0; i < 4; i++) {
            if (i < reponses.size()) {
                radioButtons[i].setText(reponses.get(i).getRep());
                radioButtons[i].setDisable(false);  // Activer le RadioButton s'il y a une réponse
            } else {
                radioButtons[i].setText("");  // Réinitialiser le texte si aucune réponse
                radioButtons[i].setDisable(true);  // Désactiver le RadioButton s'il n'y a pas de réponse
            }
        }
    }


    /*private void afficherQuestionActuelle() {

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

    }*/

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

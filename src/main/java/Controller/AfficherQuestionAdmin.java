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
import javafx.scene.control.*;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;

public class AfficherQuestionAdmin {

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
    private RadioButton radioButton1;

    @FXML
    private RadioButton radioButton2;

    @FXML
    private RadioButton radioButton3;

    @FXML
    private RadioButton radioButton4;
    @FXML
    private Button confirmer;
    private List<Questions> listeQuestions;
    private int indexQuestionActuelle;

    private Questions questionActuelle;

    private String selectedAnswer;

    private int quizScore;
   /* @FXML
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
    }*/


    @FXML
    void selectAnswer(ActionEvent event) {
        RadioButton selectedRadioButton = getSelectedRadioButton();
        if (selectedRadioButton != null) {
            selectedAnswer = selectedRadioButton.getText();
        }
    }

    private RadioButton getSelectedRadioButton() {
        if (radioButton1.isSelected()) {
            return radioButton1;
        } else if (radioButton2.isSelected()) {
            return radioButton2;
        } else if (radioButton3.isSelected()) {
            return radioButton3;
        } else if (radioButton4.isSelected()) {
            return radioButton4;
        }
        return null;
    }



    private boolean checkIfAnswerIsCorrect(String selectedAnswer) {
        Questions questionActuelle = listeQuestions.get(indexQuestionActuelle);

        for (Reponses reponse : questionActuelle.getListeRep()) {
            if (reponse.getRep().equals(selectedAnswer) && reponse.isStatut()) {
                return true; // La réponse est correcte
            }
        }
        return false; // La réponse est incorrecte
    }

    /*private void updateQuizScore(boolean isCorrect) {
        if (isCorrect) {
            // Si la réponse est correcte, incrémentez la note du quiz
            AvancementService avancementService = new AvancementService();
            Avancement avancement = avancementService.getAvancementByStudentAndQuestion(
                    questionActuelle.getId(), questionActuelle.getIdquiz()
            );

            if (avancement != null) {
                avancement.setNoteQuiz(avancement.getNoteQuiz() + 1);
                avancementService.addEntity(avancement);
            }
        }
        // Sinon, la note reste la même
    }*/


    @FXML
    void confirmer(ActionEvent event) {
        System.out.println("Confirm button clicked...");

        if (getSelectedRadioButton() == null) {
            showAlert("Veuillez choisir une réponse avant de confirmer.");
            return; // Sortir de la méthode si aucun RadioButton n'est sélectionné
        }

       boolean isCorrect = checkIfAnswerIsCorrect(selectedAnswer);
       if(isCorrect){quizScore++;}

        // Réinitialiser les RadioButtons
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);

        toggleGroup.selectToggle(null);
        questionSuivante();
        System.out.println("User selected: " + selectedAnswer);




    }


    @FXML
    void initialize() {

        if (listeQuestions != null && !listeQuestions.isEmpty()) {

            indexQuestionActuelle=0;

            afficherQuestionActuelle();


        }

        else {
            System.out.println("Aucune question à afficher.");
        }

    }
    private void afficherQuestionActuelle() {
         questionActuelle = listeQuestions.get(indexQuestionActuelle);
        System.out.println("Afficher la question actuelle");

        quest.setText(questionActuelle.getQuest());
        quest.setMouseTransparent(true);
        quest.setEditable(false);

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




    // Méthode pour passer à la question suivante
    public void questionSuivante() {
        if (indexQuestionActuelle < listeQuestions.size() - 1) {
            indexQuestionActuelle++;
            System.out.println("Displaying next question...");
            afficherQuestionActuelle();
        }
        else {
            showAlert("Fin des questions. Votre note est: " + quizScore+ " points");
            System.out.println("Fin des questions.");

            // Ajoutez ici la logique pour gérer la fin des questions si nécessaire
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
















 /*List<Reponses> reponses = questionActuelle.getListeRep();
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

*/
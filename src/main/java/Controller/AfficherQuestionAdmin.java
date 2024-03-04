package Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Avancement;
import Entities.Questions;
import Entities.Reponses;
import Services.AvancementService;
import Services.QuestionsServices;
import Utils.MyConnection;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.sound.midi.Soundbank;

public class AfficherQuestionAdmin {

    @FXML
    private ResourceBundle resources;


    @FXML
    private Button statistiques;

    @FXML
    private URL location;

    @FXML
    private VBox qrbox;

    @FXML
    private Button qrcode;


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

    private int noteFinal;

    private int tempsRestant;
    private Timeline timeline;

    @FXML
    private Label tempsRes;

    @FXML
    private Button retour;





    @FXML
    void qrcode(ActionEvent event) {
        // Générer le QR Code avec le résultat du quiz
        String resultText = "Votre note est : " + (noteFinal-2) + " points";
        ByteArrayOutputStream byteArrayOutputStream = QRCode.from(resultText).to(ImageType.PNG).stream();

        // Convertir le flux d'octets en Image
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Image qrCodeImage = new Image(byteArrayInputStream);

        // Créer une ImageView pour afficher l'image QR Code
        ImageView imageView = new ImageView(qrCodeImage);

        // Ajouter l'ImageView au VBox qrbox
        qrbox.getChildren().add(imageView);
    }
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
        //    System.out.println(reponse.getRep());
         //   System.out.println(reponse.isStatut());
            if (reponse.getRep().equals(selectedAnswer) && reponse.isStatut()== true) {

                return false; // La réponse est correcte
            }
        }
        return true; // La réponse est incorrecte
    }




    @FXML
    void confirmer(ActionEvent event) {
        // Réinitialiser le temps restant à 60 secondes
        tempsRestant = 60;
        // Arrêter la timeline précédente s'il y en a une
        if (timeline != null) {
            timeline.stop();
        }
        // Redémarrer la timeline
        timeline.play();
        System.out.println("Confirm button clicked...");
        if (getSelectedRadioButton() == null) {
            showAlert("Veuillez choisir une réponse avant de confirmer.");
            return; // Sortir de la méthode si aucun RadioButton n'est sélectionné
        }
       boolean isCorrect = checkIfAnswerIsCorrect(selectedAnswer);
       if(isCorrect){
           quizScore++;
           }
        noteFinal=quizScore;
        System.out.println("Note final: "+ noteFinal);
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
    void retour(ActionEvent event) {
        // Réinitialiser les RadioButtons
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(null);
        questionPrecedente();
    }


    @FXML
    void initialize() {
        quest.setMouseTransparent(true);
        quest.setEditable(false);
        rep1.setText("");
        rep2.setText("");
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
        quizScore=0;

        if (listeQuestions != null && !listeQuestions.isEmpty()) {
            indexQuestionActuelle = 0;

        } else {
            System.out.println("Aucune question à afficher.");
        }



        tempsRestant = 60; // par exemple, 60 secondes

        // Configurer la timeline pour mettre à jour le temps restant chaque seconde
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    tempsRestant--;
                    // Mettre à jour l'interface utilisateur avec le temps restant
                    // Par exemple, mettre à jour un Label
                    tempsRes.setText("Temps restant : " + tempsRestant + "s");

                    if (tempsRestant == 0) {

                        // Si le temps est écoulé, passer à la question suivante
                        questionSuivante();

                        tempsRestant = 60;
                    }
                })
        );

        // Configurer la timeline pour qu'elle se répète indéfiniment
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Démarrer la timeline lorsque la question est affichée
        timeline.play();
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
       // timeline.stop();
        if (indexQuestionActuelle < listeQuestions.size() - 1) {
            indexQuestionActuelle++;
            System.out.println("Displaying next question...");
            afficherQuestionActuelle();
        }
        else {
            showAlert("Fin des questions.");
            tempsRestant=0;
            timeline.stop();

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
package Controller;
import Entities.Questions;
import Services.QuestionsServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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

    @FXML
    private Label moy;
//private Quiz quiz;

    TableColumn<Quiz, Void> deleteColumn = new TableColumn<>("Quiz");
    TableColumn<Quiz, Void> addColumn = new TableColumn<>("Ajouter question");


  /*  private void ajouterQuestionInterface(Object quiz){
        try{



            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterQuestion.fxml"));
            Parent root = loader.load();
            Stage registerStage = new Stage();
            AjouterQuestion controller = loader.getController();
            controller.setQuiz(quiz);

            registerStage.setScene(new Scene(root));
            registerStage.show();
        }
        catch (IOException e) {e.printStackTrace();}
    }
*/


    private void showQuestionsInterface(Quiz quiz) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuestionAdmin.fxml"));
            Parent root = loader.load();

            AfficherQuestionAdmin controller = loader.getController();

            QuestionsServices questionsServices = new QuestionsServices(MyConnection.getInstance().getCnx());
            List<Questions> questionsList = questionsServices.getQuestionsByQuizId(quiz.getId());

            controller.setListeQuestions(questionsList);

            // Pass the selected quiz to the next controller if needed
            //controller.setSelectedQuiz(quiz);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            // Make the new stage a modal window
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(deleteColumn.getTableView().getScene().getWindow());

            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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
        deleteColumn.setCellFactory(ButtonTableCell.forTableColumn("Questions", user ->
        {
            showQuestionsInterface(user);
        }));
     //   addColumn.setCellValueFactory(ButtonTableCell.forTableColumn1("Ajouter",()->{
       // ajouterQuestionInterface();
      //  }));

  //    addColumn.setCellValueFactory(ButtonTableCell.forTableColumn1("Ajouter", (quiz) -> {ajouterQuestionInterface(quiz);}));
        table.getColumns().addAll(deleteColumn);
     //   table.getColumns().addAll(addColumn);



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

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Modification du quiz");
        confirmationAlert.setContentText("Voulez-vous vraiment modifier ce quiz ?");

        ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(ouiButton, nonButton);


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ouiButton) {
            String requete = "UPDATE quiz SET nom=?, nbQuest=?,note=? WHERE id=?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setString(1, nom);
                pst.setInt(2, nbQuest);
                pst.setInt(3, note);
                pst.setInt(4, idd);

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
    }

    @FXML
    void supprimer(ActionEvent event) {

        Quiz clickedquiz =  table.getSelectionModel().getSelectedItem();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Suppression du quiz");
        confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce quiz ?");

        ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(ouiButton, nonButton);

        // Option pour attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ouiButton) {
            table.getItems().remove(clickedquiz);
            QuizServices quizServices = new QuizServices();
            quizServices.deleteEntity(clickedquiz);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le quiz a été supprimé avec succés");
            alert.show();
        }
    }
    @FXML
    void quiz(MouseEvent event) {
        Quiz clickedquiz =  table.getSelectionModel().getSelectedItem();
        nomU.setText(clickedquiz.getNom());
        nbQuestU.setText(String.valueOf(clickedquiz.getNbQuest()));
        noteU.setText(String.valueOf(clickedquiz.getNote()));

        int notee =Integer.parseInt(noteU.getText());
        int nbQuestt=Integer.parseInt(nbQuestU.getText());
        double moye=(double) notee/nbQuestt;
        moy.setText("Note par Question: " + moye);

    }
}

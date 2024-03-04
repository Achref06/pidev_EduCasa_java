package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Questions;
import Entities.Quiz;
import Services.QuestionsServices;
import Services.QuizServices;
import Utils.MyConnection;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AfficherQuizUser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField chercherKey;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private Label moy;

    @FXML
    private TableColumn<?, ?> nbQuest;

    @FXML
    private TableColumn<?, ?> nom;

    @FXML
    private TableColumn<?, ?> note;

    @FXML
    private TableView<Quiz> table;

    TableColumn<Quiz, Void> deleteColumn = new TableColumn<>("Quiz");

    private final QuizServices qs=new QuizServices();

    @FXML
    void quiz(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Don't touch this!");
        alert.show();

    }

    @FXML
    void initialize() {
        afficherQuiz();

        FilteredList<Quiz> filteredData = new FilteredList<>(qs.getAllData(), b -> true);

        chercherKey.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                // If filter text is empty, display all users.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare user name with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                }
                return false; // Does not match.
            });
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Quiz> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        table.setItems(sortedData);


    }




    private void showQuestionsInterface(Quiz quiz) {

        QuestionsServices questionsServices = new QuestionsServices(MyConnection.getInstance().getCnx());
        List<Questions> questionsList = questionsServices.getQuestionsByQuizId(quiz.getId());
        System.out.println(quiz.getId());

        if (questionsList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucune question dans cette liste");
            alert.show();
        }
        else {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuestionAdmin.fxml"));
                Parent root = loader.load();
                AfficherQuestionAdmin controller = loader.getController();
                controller.setListeQuestions(questionsList);
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));

                // Make the new stage a modal window
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(deleteColumn.getTableView().getScene().getWindow());

                newStage.showAndWait();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
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
        table.getColumns().addAll(deleteColumn);
        table.setItems(quizServices.getAllData());

    }

}

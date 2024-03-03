package Controller;

import Entities.Questions;
import Services.QuestionsServices;
import Utils.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Home extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
     //  QuestionsServices questionsServices=new QuestionsServices(MyConnection.getInstance().getCnx());
      //  List<Questions> questionsList=questionsServices.getAllData();
      //  System.out.println("Number of questions: "+questionsList.size());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherQuiz.fxml"));
        try {
            Parent root=loader.load();
       //     AfficherQuestion controller=loader.getController();
       //   controller.setListeQuestions(questionsList);
            Scene scene= new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

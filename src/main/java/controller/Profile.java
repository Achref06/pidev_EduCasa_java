package controller;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.UserServices;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static controller.login.UserConnected;

public class Profile implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button cancelButton;

    private UserServices userServices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userServices = new UserServices();
        populateUserInfo();
        gridPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    private void populateUserInfo() {
        User user = userServices.getUserByEmail(UserConnected);
        if (user != null) {
            gridPane.addRow(0, new Text("Nom:"), new Text(user.getNom()));
            gridPane.addRow(1, new Text("Prénom:"), new Text(user.getPrenom()));
            gridPane.addRow(2, new Text("Email:"), new Text(user.getEmail()));
            gridPane.addRow(3, new Text("Rôle:"), new Text(user.getRole()));
            gridPane.addRow(4, new Text("Spécialité:"), new Text(user.getSpecialite()));
            gridPane.addRow(5, new Text("Niveau:"), new Text(user.getNiveau()));
            gridPane.addRow(6, new Text("Mot de passe:"), new Text(user.getMdp()));


            ((Text) gridPane.getChildren().get(1)).setId("nomText");
            ((Text) gridPane.getChildren().get(3)).setId("prenomText");
            ((Text) gridPane.getChildren().get(5)).setId("emailText");
            ((Text) gridPane.getChildren().get(7)).setId("roleText");
            ((Text) gridPane.getChildren().get(9)).setId("specialiteText");
            ((Text) gridPane.getChildren().get(11)).setId("niveauText");
            ((Text) gridPane.getChildren().get(13)).setId("mdpText");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    void updateButtonOnAction(ActionEvent event) {
        User user = userServices.getUserByEmail(UserConnected);
        if (user != null) {
            user.toString();
            openUpdateUserForm(user);
        }
    }

    private void openUpdateUserForm(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUser.fxml"));
            //UpdateUser updateUser = loader.getController(); // Get the controller instance
            //updateUser.setUser(user); // Set the User object
            Parent root = loader.load();
            Stage updateUserStage = new Stage();
            updateUserStage.setScene(new Scene(root));
            updateUserStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
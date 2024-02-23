package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Showrecetud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> showreclamation;

    @FXML
    private TableColumn<?, ?> desccolone;

    @FXML
    private TableColumn<?, ?> typecolone;

    @FXML
    private TableColumn<?, ?> datecolone;

    @FXML
    private Button ajouterrec;

    @FXML
    private Button modreclamation;

    @FXML
    private Button suppreclamation;

    @FXML
    void handleDeleteButtonButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/deleterelamation.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleUpdateButtonButton(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/modifreclamation.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleajouterButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ajouterreclamation.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void initialize() {

    }
}

package tn.esprit.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.reclamation;
import tn.esprit.services.reclamationService;
import tn.esprit.services.traitementServices;
import tn.esprit.entities.traitement;

public class Deletetraitment {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idTraitement;

    @FXML
    private Button supprimertraitement;

    @FXML
    void handeldeltraitbuton(ActionEvent event) throws IOException {
        traitement traitement = new traitement(idTraitement.getText());
        traitementServices traitementServices = new traitementServices();
        try {
            traitementServices.addT(traitement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("traitement supprimee ");
        alert.show();

        Parent root = FXMLLoader.load(getClass().getResource("/showtraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

    }

    /*@FXML
    void handeldeltraitbuton(ActionEvent event) throws IOException {
        traitement traitement = new traitement(idTraitement.getText());
        traitementServices traitementServices = new traitementServices();
        try {
            traitementServices.addT(traitement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("traitement supprimee ");
        alert.show();

        Parent root = FXMLLoader.load(getClass().getResource("/showrecetud.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }*/

    @FXML
    void initialize() {}
}


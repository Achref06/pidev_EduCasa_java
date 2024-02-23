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
import tn.esprit.entities.traitement;
import tn.esprit.services.reclamationService;
import tn.esprit.services.traitementServices;

public class Modifiertraitement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addTmodif;

    @FXML
    private TextField numRmodif;

    @FXML
    private TextField reponsemodif;

    @FXML
    private Button modifiertraitement;

    @FXML
    void handelmodtraitbuton(ActionEvent event) throws IOException {
        traitement traitement = new traitement(reponsemodif.getText());
        traitementServices traitementServices = new traitementServices();
        try {
            traitementServices.updateT(traitement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("traitement modifiee ");
        alert.show();

        Parent root = FXMLLoader.load(getClass().getResource("/showtraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void modifiertraitement(ActionEvent event) {
        traitement traitement = new traitement(addTmodif.getText(), numRmodif.getText(), reponsemodif.getText());
        traitementServices traitementServices = new traitementServices();
        try {
            traitementServices.addT(traitement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("reclamation modifier ");
        alert.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifreclamation.fxml"));
        try {
            Parent root = loader.load();
            Reclamationinfo reclamationinfo = loader.getController();
            reclamationinfo.setDescription(addTmodif.getText());
            reclamationinfo.setType(numRmodif.getText());
            reclamationinfo.setDate(reponsemodif.getText());
            addTmodif.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    /*@FXML
    void initialize() {

    }*/
    }
}

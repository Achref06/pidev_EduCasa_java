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

public class Deleterelamation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idtextfeild;

    @FXML
    private Button supprimerreclamation;

    @FXML
    void supprimerreclamation(ActionEvent event) {
        reclamation reclamation = new reclamation(idtextfeild.getText());
        reclamationService reclamationService = new reclamationService();
        try {
            reclamationService.delete(reclamation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("reclamation ajoutee ");
        alert.show();

    }


    public void handleSuppButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/showrecprof.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }
}



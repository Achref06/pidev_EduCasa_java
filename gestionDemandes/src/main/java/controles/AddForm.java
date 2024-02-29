package controles;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Form;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import services.FormServices;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class AddForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
     DatePicker dateTextField;

    @FXML
    private Button donnees;

    @FXML
     ChoiceBox<String> statutTextField;

    @FXML
    void ajouterFormulaire(ActionEvent event) {
        LocalDate selectedDate = dateTextField.getValue();

        // Retrieve the selected statut from the ChoiceBox
        String selectedStatut = statutTextField.getValue();

        // Perform validation to ensure that both date and statut are selected
        if (selectedDate == null || selectedStatut == null) {
            // Show an error message if either date or statut is not selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner une date et un statut.");
            alert.show();
            return;
        }
        Form form = new Form(selectedDate,selectedStatut);
        FormServices formServices = new FormServices();
        formServices.addEntity(form);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le formulaire a été ajouté avec succès.");
        alert.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormInfo.fxml"));
        try {
            Parent root = loader.load();
            FormInfo formInfo = loader.getController();
            formInfo.setDate(String.valueOf(dateTextField.getValue()));
            formInfo.setStatut(statutTextField.getValue());
            statutTextField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void initialize() {
        statutTextField.getItems().add("traité");
        statutTextField.getItems().add("non traité");
    }

}

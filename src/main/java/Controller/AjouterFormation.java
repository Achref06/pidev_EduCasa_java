package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import Entities.Formation;
import Services.FormationServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class AjouterFormation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField DureeTextField;

    @FXML
    private TextField NiveauTextField;

    @FXML
    private TextField NomTextField;

    @FXML
    private TextField nbjoursTextField;

    @FXML
    void ajouterFormation(ActionEvent event) {
        Formation Formation=new Formation(NomTextField.getText(),DureeTextField.getText(),NiveauTextField.getText(),Integer.parseInt(nbjoursTextField.getText()));
        FormationServices fs =new FormationServices();
        fs.ajouterEntity(Formation);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("la formation a ete ajoutée avec succés");
        alert.show();

    }

    @FXML
    void initialize() {

        UnaryOperator<TextFormatter.Change> numberFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*") || text.isEmpty()) {
                return change;
            } else {
                showAlert("Veuillez saisir uniquement des chiffres.");
                return null;
            }
        };


        TextFormatter<String> numberFormatter = new TextFormatter<>(numberFilter);


        nbjoursTextField.setTextFormatter(numberFormatter);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[a-zA-Z\\s]+") || text.isEmpty()) {
                return change;
            } else {
                showAlert("Veuillez saisir uniquement des lettres.");
                return null;
            }
        };


        TextFormatter<String> formatter = new TextFormatter<>(filter);

        NomTextField.setTextFormatter(formatter);
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    }



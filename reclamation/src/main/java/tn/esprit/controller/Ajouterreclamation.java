
package tn.esprit.controller;

import javafx.scene.control.ChoiceBox;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.services.reclamationService;
import tn.esprit.entities.reclamation;
        import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;

public class Ajouterreclamation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button ajouterreclamation;

    @FXML
    private TextField descTextfeild;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ChoiceBox<String> typeadd;


    private final reclamationService rs = new reclamationService();

    @FXML
    void handelajouterButon(ActionEvent event) throws IOException {
        reclamation reclamation = new reclamation(descTextfeild.getText(),  typeadd.getValue(), datepicker.getValue());
        reclamationService reclamationService = new reclamationService();
        try {
            reclamationService.add(reclamation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("reclamation ajoutee ");
        alert.show();

        Parent root = FXMLLoader.load(getClass().getResource("/showrecetud.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void initialize() {
        typeadd.getItems().add("cours");
        typeadd.getItems().add("prof");
        typeadd.getItems().add("etudiant");

    }




    }









package tn.esprit.controller;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.control.Button;
import javafx.scene.control.TextField;
        import javafx.scene.control.Alert;
        import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.entities.reclamation;
        import tn.esprit.services.reclamationService;


public class modifreclamation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descmodif;

    @FXML
    private ChoiceBox<String> typemodif;

    @FXML
    private DatePicker datemodif;

    @FXML
    private Button modifierreclamation;


    @FXML
    void handleupdateButton(ActionEvent event) throws IOException {
        reclamation reclamation = new reclamation(descmodif.getText());
        reclamationService reclamationService = new reclamationService();
        try {
            reclamationService.add(reclamation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("reclamation modifiee ");
        alert.show();

        Parent root = FXMLLoader.load(getClass().getResource("/showrecetud.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

    }


       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationinfo.fxml"));
        try {
            Parent root = loader.load();
            Reclamationinfo reclamationinfo = loader.getController();
            reclamationinfo.setDescription(descmodif.getText());
            reclamationinfo.setType((String) typemodif.getValue());
            reclamationinfo.setDate(datemodif.getValue());
            descmodif.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        @FXML
        void initialize () {
            typemodif.getItems().add("cours");
            typemodif.getItems().add("prof");
            typemodif.getItems().add("etudiant");
        }


}

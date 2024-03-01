package controles;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Infos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.InfosServices;

public class RatingProf {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Infos, String> matiere;

    @FXML
    private TextField matiereTextField;

    @FXML
    private TableColumn<Infos, String> nom;

    @FXML
    private TextField nomTextField;

    @FXML
    private TableColumn<Infos, String> prenom;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TableView<Infos> showDonnees;

    @FXML
    private TextField qualityField;

    @FXML
    private TextField interactionField;

    @FXML
    private TextField preparationField;

    @FXML
    private TextField evaluationsField;

    @FXML
    private Button calculateButton;

    @FXML
    private TextField ratingField;

    @FXML
    void calculateRating() {
        // Retrieve the values of the text fields
        double quality = Double.parseDouble(qualityField.getText());
        double interaction = Double.parseDouble(interactionField.getText());
        double preparation = Double.parseDouble(preparationField.getText());
        double evaluations = Double.parseDouble(evaluationsField.getText());

        // Define weights for each criterion
        double qualityWeight = 0.4;
        double interactionWeight = 0.3;
        double preparationWeight = 0.2;
        double evaluationsWeight = 0.1;

        // Calculate the weighted sum of the criteria
        double weightedQuality = quality * qualityWeight;
        double weightedInteraction = interaction * interactionWeight;
        double weightedPreparation = preparation * preparationWeight;
        double weightedEvaluations = evaluations * evaluationsWeight;

        // Calculate the overall rating
        double overallRating = weightedQuality + weightedInteraction + weightedPreparation + weightedEvaluations;
        Infos selectedProf = showDonnees.getSelectionModel().getSelectedItem();
        if (selectedProf != null) {
            // Display the calculated rating in the corresponding text field
            ratingField.setText(String.valueOf(overallRating));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun professeur sélectionné.");
            alert.show();
        }
    }

    @FXML
    void initialize() {
        final InfosServices is = new InfosServices();
        List<Infos> infos = is.getDataProf();
        ObservableList<Infos> observableList = FXCollections.observableList(infos);
        showDonnees.setItems(observableList);
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        matiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        showDonnees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Infos selectedInfos = showDonnees.getSelectionModel().getSelectedItem();

                String selectedNom = selectedInfos.getNom();
                String selectedPrenom = selectedInfos.getPrenom();
                String selectedMatiere = selectedInfos.getMatiere();

                nomTextField.setText(selectedNom);
                prenomTextField.setText(selectedPrenom);
                matiereTextField.setText(selectedMatiere);
            }
        });
    }
}
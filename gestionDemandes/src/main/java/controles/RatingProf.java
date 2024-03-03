package controles;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entities.Infos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.InfosServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    private TextField rateProf;

    @FXML
    void calculateRating(ActionEvent event) {
        double calculatedRating;
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
            calculatedRating = overallRating;
            ratingField.setText(String.valueOf(calculatedRating));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun professeur sélectionné.");
            alert.show();
        }
    }

    @FXML
    void rateProf(ActionEvent event) {
        try {
            // Get the selected item from the list
            Infos selectedInfo = showDonnees.getSelectionModel().getSelectedItem();

            if (selectedInfo != null) {
                // Extract necessary information from the selected item
                int professorId = selectedInfo.getIdP(); // Assuming there's a method to retrieve professor ID
                String rating = String.valueOf(getRatingField()); // Assuming there's a method to retrieve professor's current rating

                // Send the rating request to the API
                HttpResponse<String> response = Unirest.get("https://movies-ratings2.p.rapidapi.com/ratings?id=tt1798709")
                        .header("X-RapidAPI-Key", "f8d5464b90msh67ca564c4447214p1a050ajsn81238dfd0b94")
                        .header("X-RapidAPI-Host", "movies-ratings2.p.rapidapi.com")
                        .asString();

                // Handle the response here if needed
                int status = response.getStatus();
                String responseBody = response.getBody();
                System.out.println("Response status: " + status);
                System.out.println("Response body: " + responseBody);
                String score = extractScoreFromResponse(responseBody);

                // Display the score in the rateProf text field
                String rate = ratingField.getText();
                rateProf.setText(rate);

            } else {
                System.out.println("No item selected.");
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public TextField getRatingField() {
        return ratingField;
    }

    private String extractScoreFromResponse(String responseBody) {
        return responseBody;
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
package controles;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entities.Infos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.InfosServices;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

public class ShowInfos {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Infos, String> dateN;

    @FXML
    private TableColumn<Infos, String> diplome;

    @FXML
    private TableColumn<Infos, String> email;

    @FXML
    private TableColumn<Infos, String> experience;

    @FXML
    private Button formulaire;

    @FXML
    private TableColumn<Infos, Integer> idF;

    @FXML
    private TableColumn<Infos, Integer> idP;

    @FXML
    private TableColumn<Infos, String> matiere;

    @FXML
    private TableColumn<Infos, String> motivation;

    @FXML
    private TableColumn<Infos, String> nom;

    @FXML
    private TableColumn<Infos, String> prenom;

    @FXML
    private TableView<Infos> showDonnees;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField ageField;

    @FXML
    void afficherFormulaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowForm.fxml"));
            Parent nextInterfaceParent = loader.load();
            Scene nextInterfaceScene = new Scene(nextInterfaceParent);

            // Get the stage
            Stage stage = (Stage) formulaire.getScene().getWindow();

            // Set the new scene
            stage.setScene(nextInterfaceScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchInfos(ActionEvent event) {
        String searchText = searchTextField.getText().trim();

        // Call the search method from the service class
        InfosServices infosServices = new InfosServices();
        List<Infos> searchResults = infosServices.searchInfos(searchText);

        // Display the search results in the table view
        ObservableList<Infos> observableList = FXCollections.observableList(searchResults);
        showDonnees.setItems(observableList);
    }

    @FXML
    void trierDonnees(ActionEvent event) {
        final InfosServices is = new InfosServices();
        List<Infos> infos = is.getAllDataSortedByMatiere();
        ObservableList<Infos> observableList = FXCollections.observableList(infos);
        showDonnees.setItems(observableList);
    }

    @FXML
    void calculateAge(ActionEvent event) {
        Infos selectedProf = showDonnees.getSelectionModel().getSelectedItem();

        // Vérifier si un professeur est sélectionné
        if (selectedProf != null) {
            // Récupérer la date de naissance du professeur
            LocalDate dateNaissance = selectedProf.getDateN();

            // Calculer l'âge en années à partir de la date de naissance
            LocalDate currentDate = LocalDate.now();
            Period agePeriod = Period.between(dateNaissance, currentDate);
            int age = agePeriod.getYears();

            // Afficher l'âge calculé dans le champ de texte correspondant
            ageField.setText(String.valueOf(age));
        } else {
            // Aucun professeur sélectionné, afficher un message d'erreur ou gérer en conséquence
            System.out.println("Aucun professeur sélectionné.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun professeur sélectionné.");
            alert.show();
        }
    }

    @FXML
    void detectIngenieur(ActionEvent event) {
        try {
            // Get the selected item from the TableView
            Infos selectedInfo = showDonnees.getSelectionModel().getSelectedItem();

            if (selectedInfo != null) {
                // Send OCR request for the selected item's URL
                HttpResponse<String> response = Unirest.post("https://ocr43.p.rapidapi.com/v1/results")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("X-RapidAPI-Key", "f8d5464b90msh67ca564c4447214p1a050ajsn81238dfd0b94")
                        .header("X-RapidAPI-Host", "ocr43.p.rapidapi.com")
                        .body("url=" + selectedInfo.getDiplome())
                        .asString();

                // Handle the response
                int status = response.getStatus();
                String responseBody = response.getBody();
                System.out.println("Response status: " + status);
                System.out.println("Response body: " + responseBody);


                // Extract diplome text from the response (replace this with the actual parsing logic based on your response format)
                String diplome = extractDiplomeFromResponse(responseBody);


                // Check if "ingenieur" is detected in the diplome
                if (diplome != null && (diplome.toLowerCase().contains("ingenieur") || diplome.toUpperCase().contains("INGENIEUR")) || diplome.contains("ingénieur") || diplome.contains("Ingénieur")) {
                    System.out.println("The word 'ingenieur' is detected in the selected diplome.");
                    // Perform further actions if needed
                } else {
                    System.out.println("The word 'ingenieur' is not detected in the selected diplome.");
                }
            } else {
                System.out.println("No item selected.");
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private String extractDiplomeFromResponse(String responseBody) {
        return responseBody;
    }

    @FXML
    void sendMail(ActionEvent event) {
        try {
            // Get the selected item from the TableView
            Infos selectedInfo = showDonnees.getSelectionModel().getSelectedItem();

            if (selectedInfo != null) {
                String recipientEmail = selectedInfo.getEmail(); // Assuming getEmail() retrieves the email address from the selected item

                // Send the OCR request to check if "ingenieur" is detected
                HttpResponse<String> response = Unirest.post("https://ocr43.p.rapidapi.com/v1/results")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("X-RapidAPI-Key", "f8d5464b90msh67ca564c4447214p1a050ajsn81238dfd0b94")
                        .header("X-RapidAPI-Host", "ocr43.p.rapidapi.com")
                        .body("url=" + selectedInfo.getDiplome())
                        .asString();

                // Handle the OCR response
                int status = response.getStatus();
                String responseBody = response.getBody();
                System.out.println("OCR Response status: " + status);
                System.out.println("OCR Response body: " + responseBody);

                // Check if "ingenieur" is detected in the OCR response
                boolean ingenieurDetected = responseBody.toLowerCase().contains("ingenieur");

                // Define the email body message based on the condition
                String emailBody;
                if (ingenieurDetected) {
                    emailBody = "Votre formulaire est accepté.";
                } else {
                    emailBody = "Votre formulaire est en attente de traitement.";
                }

                // Send the email with the appropriate body message
                HttpResponse<String> mailResponse = Unirest.post("https://mail-sender-api1.p.rapidapi.com/")
                        .header("content-type", "application/json")
                        .header("X-RapidAPI-Key", "f8d5464b90msh67ca564c4447214p1a050ajsn81238dfd0b94")
                        .header("X-RapidAPI-Host", "mail-sender-api1.p.rapidapi.com")
                        .body("{\r\n    \"sendto\": \"" + recipientEmail + "\",\r\n    \"name\": \"Molka\",\r\n    \"replyTo\": \"molka.m2001@gmail.com\",\r\n    \"ishtml\": \"false\",\r\n    \"title\": \"État de votre formulaire\",\r\n    \"body\": \"" + emailBody + "\"\r\n}")
                        .asString();

                // Handle the mail response
                int mailStatus = mailResponse.getStatus();
                String mailResponseBody = mailResponse.getBody();
                System.out.println("Mail Response status: " + mailStatus);
                System.out.println("Mail Response body: " + mailResponseBody);

            } else {
                System.out.println("No item selected.");
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        final InfosServices is =new InfosServices();
        List<Infos> infos = is.getAllData();
        ObservableList<Infos> observableList= FXCollections.observableList(infos);
        showDonnees.setItems(observableList);
        idP.setCellValueFactory(new PropertyValueFactory<>("idP"));
        idF.setCellValueFactory(new PropertyValueFactory<>("idF"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateN.setCellValueFactory(new PropertyValueFactory<>("dateN"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        motivation.setCellValueFactory(new PropertyValueFactory<>("motivation"));
        matiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        diplome.setCellValueFactory(new PropertyValueFactory<>("diplome"));
        diplome.setCellFactory(column -> {
            return new TableCell<Infos, String>() {
                final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        // Load the image and set it to the ImageView
                        Image image = new Image(new File(item).toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(100); // Set width as needed
                        imageView.setFitHeight(100); // Set height as needed
                        setGraphic(imageView);
                    }
                }
            };
        });
    }

}

package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Form;
import entities.Infos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.FormulaireDAO;
import services.InfosServices;
import utils.MyConnection;

import static java.lang.Integer.parseInt;

public class AddInfos {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateNText;

    @FXML
    private TextField diplomeTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField experienceTextField;

    @FXML
    private ChoiceBox<String> matiereTextField;

    @FXML
    private TextField motivationTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField idFTextField;

    @FXML
    void ajouterDonnees(ActionEvent event) {
        LocalDate selectedDate = dateNText.getValue();

        // Retrieve the selected statut from the ChoiceBox
        String selectedMatiere = matiereTextField.getValue();


        // Perform validation to ensure that both date and statut are selected
        if (selectedDate == null || selectedMatiere == null) {
            // Show an error message if either date or statut is not selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner une date et une matiere.");
            alert.show();
            return;
        }
        String email = emailTextField.getText();

        // Validate the email address using a regular expression
        if (!isValidEmail(email)) {
            // Show an error message if the email address is not valid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez saisir une adresse email valide.");
            alert.show();
            return;
        }
        email = emailTextField.getText();
        // Ajouter d'autres champs si nécessaire

        // Vérifier si un formulaire avec e-mail existe déjà dans la base de données
        if (formulaireExisteDeja(email)) {
            // Afficher un message d'erreur indiquant que le formulaire existe déjà
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Un formulaire avec e-mail existe déjà.");
            alert.show();
        } else {
            int idF = parseInt(idFTextField.getText());
            Infos infos = new Infos(idF,nomTextField.getText(), prenomTextField.getText(), selectedDate, emailTextField.getText(), experienceTextField.getText(),motivationTextField.getText(),selectedMatiere, diplomeTextField.getText());
            InfosServices infosServices = new InfosServices();
            infosServices.addEntity(infos);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le formulaire a été ajouté avec succès.");
            alert.show();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InfosInfo.fxml"));
        try {
            Parent root = loader.load();
            InfosInfo infosInfo = loader.getController();
            infosInfo.setIdF(idFTextField.getText());
            infosInfo.setNom(nomTextField.getText());
            infosInfo.setPrenom(prenomTextField.getText());
            infosInfo.setDateN(String.valueOf(dateNText.getValue()));
            infosInfo.setEmail(emailTextField.getText());
            infosInfo.setExperience(experienceTextField.getText());
            infosInfo.setMotivation(motivationTextField.getText());
            infosInfo.setMatiere(matiereTextField.getValue());
            infosInfo.setDiplome(diplomeTextField.getText());
            nomTextField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean isValidEmail(String email) {
        // Regular expression pattern for validating email addresses
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean formulaireExisteDeja(String email1) {
        boolean formulaireExiste = false;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String query = "SELECT COUNT(*) FROM donnees WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email1);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        formulaireExiste = count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formulaireExiste;
    }

    public void setId(String id) {
        this.idFTextField.setText(id);
    }
    @FXML
    void initialize() {
        matiereTextField.getItems().addAll("java","uml","math","français","anglais");
    }

}

package controles;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Infos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.InfosServices;

import static java.lang.Integer.parseInt;

public class InfosInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateNText;

    @FXML
    private Button delete;

    @FXML
    private TextField diplomeTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField experienceTextField;

    @FXML
    private TextField idFTextField;

    @FXML
    private ChoiceBox<String> matiereTextField;

    @FXML
    private TextField motivationTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private Button update;

    @FXML
    private TextField idPTextField;

    @FXML
    void modifierDonnees(ActionEvent event) {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        LocalDate dateN = dateNText.getValue();
        String email = emailTextField.getText();
        String experience = experienceTextField.getText();
        String motivation = motivationTextField.getText();
        String matiere = matiereTextField.getValue();
        String diplome = diplomeTextField.getText();
        int idF = parseInt(idFTextField.getText());
        int idP = parseInt(idPTextField.getText());

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour ces informations ?");

        // Personnaliser les boutons de la boîte de dialogue de confirmation
        ButtonType boutonOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType boutonNon = new ButtonType("Non", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(boutonOui, boutonNon);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        Optional<ButtonType> reponse = confirmationAlert.showAndWait();

        // Vérifier la réponse de l'utilisateur
        if (reponse.isPresent() && reponse.get() == boutonOui) {
            // Si l'utilisateur a cliqué sur "Oui", mettre à jour les informations
            Infos infos = new Infos(idP,idF, nom, prenom, dateN, email, experience, motivation, matiere, diplome);
            InfosServices infosServices = new InfosServices();
            infosServices.updateEntity(infos);

            // Afficher une boîte de dialogue de confirmation pour indiquer que les informations ont été mises à jour
            Alert confirmationSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationSuccessAlert.setTitle("Succès");
            confirmationSuccessAlert.setHeaderText(null);
            confirmationSuccessAlert.setContentText("Les informations ont été mises à jour avec succès.");
            confirmationSuccessAlert.showAndWait();
        }
    }

    @FXML
    void supprimerDonnees(ActionEvent event) {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        LocalDate date = dateNText.getValue();
        String email = emailTextField.getText();
        String experience = experienceTextField.getText();
        String motivation = motivationTextField.getText();
        String matiere = matiereTextField.getValue();
        String diplome = diplomeTextField.getText();
        int idF = parseInt(idFTextField.getText());
        int idP = parseInt(idPTextField.getText());

        // Créer un objet Infos avec ces informations
        Infos infosToDelete = new Infos(idP,idF,nom, prenom, date, email, experience, motivation, matiere, diplome);

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer ces informations ?");
        confirmationAlert.setContentText("Cette action est irréversible.");

        // Personnaliser les boutons de la boîte de dialogue
        ButtonType boutonOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType boutonNon = new ButtonType("Non", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(boutonOui, boutonNon);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        Optional<ButtonType> resultat = confirmationAlert.showAndWait();

        // Vérifier la réponse de l'utilisateur
        if (resultat.isPresent() && resultat.get() == boutonOui) {
            // L'utilisateur a cliqué sur "Oui", donc supprimer les informations
            InfosServices infosServices = new InfosServices();
            infosServices.deleteEntity(infosToDelete);

            // Afficher une alerte pour informer l'utilisateur de la suppression réussie
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Les informations ont été supprimées avec succès.");
            alert.show();
        } else {
            // L'utilisateur a cliqué sur "Non" ou a fermé la boîte de dialogue, ne rien faire
        }
    }

    public void setDateN(String dateN) { this.dateNText.setValue(LocalDate.parse(dateN));    }

    public void setDiplome(String diplome) {
        this.diplomeTextField.setText(diplome);
    }

    public void setEmail(String email) {
        this.emailTextField.setText(email);
    }

    public void setExperience(String experience) {
        this.experienceTextField.setText(experience);
    }

    public void setMatiere(String matiere) {
        this.matiereTextField.setValue(matiere);
    }

    public void setMotivation(String motivation) {
        this.motivationTextField.setText(motivation);
    }

    public void setNom(String nom) {
        this.nomTextField.setText(nom);
    }

    public void setPrenom(String prenom) {
        this.prenomTextField.setText(prenom);
    }

    public void setIdF(String idF) {
        this.idFTextField.setText(idF);
    }

    @FXML
    void initialize() {
        matiereTextField.getItems().addAll("java","uml","math","français","anglais");
    }

}

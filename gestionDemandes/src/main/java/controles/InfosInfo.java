package controles;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class InfosInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateN;

    @FXML
    private TextField diplome;

    @FXML
    private TextField email;

    @FXML
    private TextField experience;

    @FXML
    private ChoiceBox<String> matiere;

    @FXML
    private TextField motivation;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField idF;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    void modifierDonnees(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateInfos.fxml"));
            Parent nextInterfaceParent = loader.load();
            Scene nextInterfaceScene = new Scene(nextInterfaceParent);

            // Get the stage
            Stage stage = (Stage) update.getScene().getWindow();

            // Set the new scene
            stage.setScene(nextInterfaceScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerDonnees(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeleteInfos.fxml"));
        try {
            Parent root = loader.load();
            InfosInfo infosInfo = loader.getController();
            infosInfo.setIdF(idF.getText());
            infosInfo.setNom(nom.getText());
            infosInfo.setPrenom(prenom.getText());
            infosInfo.setDateN(String.valueOf(dateN.getValue()));
            infosInfo.setEmail(email.getText());
            infosInfo.setExperience(experience.getText());
            infosInfo.setMotivation(motivation.getText());
            infosInfo.setMatiere(matiere.getValue());
            infosInfo.setDiplome(diplome.getText());
            nom.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDateN(String dateN) { this.dateN.setValue(LocalDate.parse(dateN));    }

    public void setDiplome(String diplome) {
        this.diplome.setText(diplome);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public void setExperience(String experience) {
        this.experience.setText(experience);
    }

    public void setMatiere(String matiere) {
        this.matiere.setValue(matiere);
    }

    public void setMotivation(String motivation) {
        this.motivation.setText(motivation);
    }

    public void setNom(String nom) {
        this.nom.setText(nom);
    }

    public void setPrenom(String prenom) {
        this.prenom.setText(prenom);
    }

    public void setIdF(String idF) {
        this.idF.setText(idF);
    }

    @FXML
    void initialize() {
        matiere.getItems().addAll("java","uml","math","français","anglais");
    }

}

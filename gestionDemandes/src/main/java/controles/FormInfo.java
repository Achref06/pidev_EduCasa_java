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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class FormInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date;

    @FXML
    private Button donnees;

    @FXML
    private ChoiceBox<String> statut;

    @FXML
    void ajouterDonnees(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddInfos.fxml"));
            Parent nextInterfaceParent = loader.load();
            Scene nextInterfaceScene = new Scene(nextInterfaceParent);

            // Get the stage
            Stage stage = (Stage) donnees.getScene().getWindow();

            // Set the new scene
            stage.setScene(nextInterfaceScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDate(String date) {
        this.date.setValue(LocalDate.parse(date));
    }

    public void setStatut(String statut) {
        this.statut.setValue(statut);
    }


    @FXML
    void initialize() {

    }

}

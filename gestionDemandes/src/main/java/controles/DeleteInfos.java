package controles;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Infos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.InfosServices;

import static java.lang.Integer.parseInt;

public class DeleteInfos {

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
    private TextField idPTextField;

    @FXML
    void deleteInfos(ActionEvent event) {

    }

    @FXML
    void initialize() {
        matiereTextField.getItems().addAll("java","uml","math","français","anglais");
    }

}

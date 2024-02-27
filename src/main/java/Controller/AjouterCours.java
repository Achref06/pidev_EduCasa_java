package Controller;

import Entities.Cours;
import Services.CoursServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AjouterCours {

    @FXML
    private TextField NomCourtextField;

    @FXML
    void ajoutercour(ActionEvent event) {
        Cours cours= new Cours(NomCourtextField.getText());
        CoursServices cs =new CoursServices();
        cs.ajouterEntity(cours);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("le cour a ete ajouté avec succés");
        alert.show();
    }

}

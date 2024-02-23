package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.OffreServices;

    public class AjouterOffre {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;
        @FXML
       private TextField nomtextfield;
        @FXML
        private  TextField statuttextfield;
        @FXML
        private TextField desctextfield;
        @FXML
        private TextField prixtextfield;



        @FXML
        void ajouterOffre(ActionEvent event)  {
            Offre offre = new Offre(nomtextfield.getText(),statuttextfield.getText(),desctextfield.getText(),
                    Float.valueOf(prixtextfield.getText()));
            OffreServices offreServices = new OffreServices();
            offreServices.addEntity2(offre);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'offre est ajoutée avec succés");
            alert.show();

        }







    }




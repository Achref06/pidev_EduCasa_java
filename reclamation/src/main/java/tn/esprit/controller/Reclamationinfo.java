package tn.esprit.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Reclamationinfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField description;

    @FXML
    private TextField type ;

    @FXML
    private TextField date ;

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    @FXML
    void initialize() {

    }


}

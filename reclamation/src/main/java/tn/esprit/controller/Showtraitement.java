package tn.esprit.controller;


import tn.esprit.entities.traitement;
import tn.esprit.services.traitementServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Showtraitement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<traitement> showtraitement;

    @FXML
    private TableColumn<?, ?> numRcolone;

    @FXML
    private TableColumn<?, ?> idTcolone;

    @FXML
    private TableColumn<?, ?> reponsecolone;

    @FXML
    private Button ajoutraitement;

    @FXML
    private Button modifiertraitement;

    @FXML
    private Button supptraitement;

    private traitementServices ts;
    private List<traitement> traitements;

    @FXML
    void initialize() throws SQLException {
        final traitementServices ts = new traitementServices();
        try {
            List<traitement> traitements=ts.displayList();
            ObservableList<traitement> observableList= FXCollections.observableList(traitements);
            showtraitement.setItems(observableList);

            numRcolone.setCellValueFactory(new PropertyValueFactory<>("description"));
            idTcolone.setCellValueFactory(new PropertyValueFactory<>("type"));
            reponsecolone.setCellValueFactory(new PropertyValueFactory<>("date"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /*final traitementServices fs=new traitementServices();
        List<traitement> forms= ts.displayList();
        List<traitement> traitements;
        ObservableList<traitement> observableList= FXCollections.observableList(traitements);
        showtraitement.setItems(observableList);
        numRcolone.setCellValueFactory(new PropertyValueFactory<>("numR"));
        idTcolone.setCellValueFactory(new PropertyValueFactory<>("idT"));
        reponsecolone.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        showtraitement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                traitement selectedtrait = showtraitement.getSelectionModel().getSelectedItem();

                int selectedId = selectedtrait.getIdT();
                int selectedtraitNumR = selectedtrait.getNumR();
                String selectedreponse = selectedtrait.getReponse();


            }*/

    }

    @FXML
    void handledeleteButton(ActionEvent event) throws Exception{

        // changerInterface (ActionEvent event) throws Exception {
        // Charger la deuxième interface à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/deletetraitment.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void handelmodiftraitbuton(ActionEvent event) throws Exception{
        // changerInterface (ActionEvent event) throws Exception {
        // Charger la deuxième interface à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/modifiertraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void handletraiterButton(ActionEvent event) throws Exception {

        // changerInterface (ActionEvent event) throws Exception {
        // Charger la deuxième interface à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/ajoutertraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }


}

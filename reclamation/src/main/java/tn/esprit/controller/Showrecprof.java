package tn.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ListView;

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
import tn.esprit.entities.reclamation;
import tn.esprit.services.reclamationService;

public class Showrecprof {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button traiterrec;

    @FXML
    private Button modreclamation;

    @FXML
    private Button suppreclamation;


    /*  @FXML
      private Button addButton;*/
    @FXML
    private TableView<reclamation> showreclamation;

    @FXML
    private TableColumn<?, ?> desccolone;

    @FXML
    private TableColumn<?, ?> typecolone;

    @FXML
    private TableColumn<?, ?> datecolone;

    @FXML
    private ListView<reclamation> reclamationListView;
    private tn.esprit.services.reclamationService rs;
    private List<tn.esprit.entities.reclamation> reclamations = new ArrayList<>();
    private String title;
    private Object String;
    private java.lang.String content;
    private tn.esprit.entities.reclamation reclamation;


    @FXML
    void initialize() {


        final reclamationService rs = new reclamationService();
        try {
            List<reclamation> reclamations = rs.displayList();
            ObservableList<reclamation> observableList = FXCollections.observableList(reclamations);
            showreclamation.setItems(observableList);

            desccolone.setCellValueFactory(new PropertyValueFactory<>("description"));
            typecolone.setCellValueFactory(new PropertyValueFactory<>("type"));
            datecolone.setCellValueFactory(new PropertyValueFactory<>("date"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    // List<reclamation> reclamations= this.rs.displayList();
    // ObservableList<reclamation> observableList= FXCollections.observableList(reclamations);
    //showreclamation.setItems(observableList);*/
    //desccolone.setCellValueFactory(new PropertyValueFactory<>("descriptiion"));
    //typecolone.setCellValueFactory(new PropertyValueFactory<>("type"));
    // datecolone.setCellValueFactory(new PropertyValueFactory<>("date"));


    @FXML
    private void handletraiterButton(ActionEvent event) throws Exception {
        // changerInterface (ActionEvent event) throws Exception {
        // Charger la deuxième interface à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/showtraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }

    /*public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }*/

    public void handleDeleteButton(ActionEvent actionEvent) throws IOException, SQLException {
        // changerInterface (ActionEvent event) throws Exception {
        // Charger la deuxième interface à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/modifiertraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

        reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            rs.delete(reclamation);
            // Mettez ici le code pour supprimer la réclamation de la source de données (par exemple, une base de données)
            Showrecprof.showAlert("Reclamation supprimée", "La réclamation a été supprimée avec succès.");
        } else {
            Showrecprof.showAlert("Aucune réclamation sélectionnée", "Veuillez sélectionner une réclamation à supprimer.");
        }


    }

    private static void showAlert(java.lang.String reclamationSupprimée, java.lang.String s) {
    }
}


    /*public void handleUpdateButton(ActionEvent actionEvent) throws IOException {
        // changerInterface (ActionEvent event) throws Exception {
        // Charger la deuxième interface à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/modifreclamation.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();
    }
}*/


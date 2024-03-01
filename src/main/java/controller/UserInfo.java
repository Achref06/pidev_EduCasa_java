package controller;

import entities.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.MyConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.UserServices;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserInfo {

    ObservableList<String> specialites = FXCollections.observableArrayList("Professeur", "Etudiant");

    int index, id;


    @FXML
    private TableView<User> tableUsers;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<User, Integer> idColumn;


    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    @FXML
    private TableColumn<User, String> mdpColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> specialiteColumn;

    @FXML
    private TableColumn<User, String> niveauColumn;

    private final UserServices userServices = new UserServices();

    private ObservableList<User> users = FXCollections.observableArrayList();

    void afficher() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        mdpColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMdp()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        specialiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialite()));
        niveauColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNiveau()));

        TableColumn<User, Void> deleteColumn = new TableColumn<>("Delete");

        deleteColumn.setCellFactory(ButtonTableCell.forTableColumn("Delete", user -> {
            // Handle delete button click
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Modification du quiz");
            confirmationAlert.setContentText("Voulez-vous vraiment modifier ce quiz ?");

            ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmationAlert.getButtonTypes().setAll(ouiButton, nonButton);

            // Option pour attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ouiButton) {
                System.out.println("Delete button clicked for user: " + user);
                tableUsers.getItems().remove(user); // Remove the user from the TableView
                UserServices userServices = new UserServices();
                userServices.deleteEntity(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("L'utilisateur a été supprimé avec succés");
                alert.show();
            }
        }));


        tableUsers.getColumns().addAll(deleteColumn);

        // Fetch user data from the database and populate the TableView
        users.addAll(userServices.getAllData());
        tableUsers.setItems(users);
    }

    @FXML
    void initialize() {
        // Set the cell value factories for each column
        afficher();
    }

    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AddUser.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(ActionEvent event) {
        createAccountForm();
    }

    @FXML
    void refresh(ActionEvent event) {
        refreshTable();
    }

    private void refreshTable() {
        users.clear();
        users.addAll(userServices.getAllData());
    }

    public void cancelButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
}
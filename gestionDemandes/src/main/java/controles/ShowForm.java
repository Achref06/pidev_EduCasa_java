package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Form;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.FormServices;

public class ShowForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Form, String> date;

    @FXML
    private TextField dateTextField;

    @FXML
    private Button donnees;

    @FXML
    private TableColumn<Form, Integer> id;

    @FXML
    private TextField idTextField;

    @FXML
    private TableView<Form> showForm;

    @FXML
    private TableColumn<Form, String> statut;

    @FXML
    private TextField statutTextField;

    @FXML
    void afficherDonnees(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowInfos.fxml"));
            Parent nextInterfaceParent = loader.load();
            Scene nextInterfaceScene = new Scene(nextInterfaceParent);

            Stage stage = (Stage) donnees.getScene().getWindow();

            stage.setScene(nextInterfaceScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierFormulaire(ActionEvent event) {
        Form selectedForm = showForm.getSelectionModel().getSelectedItem();

        if (selectedForm != null) {
            // Show a confirmation alert before proceeding with modification
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setContentText("Voulez-vous vraiment modifier ce formulaire ?");

            // Show the confirmation dialog and wait for the user's response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Check if the user clicked the "OK" button
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // If the user clicked "OK", proceed with modifying the form
                selectedForm.setStatut(statutTextField.getText());

                // Refresh the table view to reflect the changes
                showForm.refresh();

                // Update the form in the database
                FormServices formServices = new FormServices();
                formServices.updateEntity(selectedForm);

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Formulaire a été modifié avec succès");
                alert.show();
            }
        } else {
            // If no form is selected, show a warning message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un formulaire à modifier.");
            alert.show();
        }
    }


    @FXML
    void supprimerFormulaire(ActionEvent event) {
        Form selectedForm = showForm.getSelectionModel().getSelectedItem();

        if (selectedForm != null) {
            // Show a confirmation alert before proceeding with deletion
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce formulaire ?");

            // Show the confirmation dialog and wait for the user's response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Check if the user clicked the "OK" button
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // If the user clicked "OK", proceed with deleting the form

                // Remove the selected form from the table view
                showForm.getItems().remove(selectedForm);

                // Delete the form from the database
                FormServices formServices = new FormServices();
                formServices.deleteEntity(selectedForm);

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Formulaire a été supprimé avec succès");
                alert.show();
            }
        } else {
            // If no form is selected, show a warning message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un formulaire à supprimer.");
            alert.show();
        }
    }

    public void setDate(String date) { this.date.setText(date); }

    public void setStatut(String statut) {
        this.statut.setText(statut);
    }

    @FXML
    void initialize() {
        final FormServices fs=new FormServices();
        List<Form> forms= fs.getAllData();
        ObservableList<Form> observableList= FXCollections.observableList(forms);
        showForm.setItems(observableList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        showForm.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Form selectedForm = showForm.getSelectionModel().getSelectedItem();

                int selectedId = selectedForm.getId();
                String selectedDate = String.valueOf(selectedForm.getDate());
                String selectedStatut = selectedForm.getStatut();

                idTextField.setText(String.valueOf(selectedId));
                dateTextField.setText(selectedDate);
                statutTextField.setText(selectedStatut);
            }
        });
    }

}

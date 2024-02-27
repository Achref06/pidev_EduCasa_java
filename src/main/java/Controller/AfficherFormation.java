package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Formation;
import Services.FormationServices;
import Utils.Myconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AfficherFormation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField DureeTextField;

    @FXML
    private TextField NiveauTextField;

    @FXML
    private TextField NomTextField;

    @FXML
    private TableColumn<Formation, String> dureeColumn;

    @FXML
    private TableColumn<Formation, Integer> idColumn;

    @FXML
    private Button modifier;

    @FXML
    private TableColumn<Formation, Integer> nbJoursColumn;

    @FXML
    private TextField nbJoursTextField;

    @FXML
    private TableColumn<Formation, String> niveauColumn;

    @FXML
    private TableColumn<Formation, String> nomColumn;

    @FXML
    private Button supprimer;

    @FXML
    private TableView<Formation> table;

    public void afficherFormation(){
        FormationServices formationServices = new FormationServices();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idFormation"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("dureeFormation"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveauFormation"));
        nbJoursColumn.setCellValueFactory(new PropertyValueFactory<>("nbJours"));
        table.setItems(formationServices.getAllData());
    }

    @FXML
    void formation(MouseEvent event) {
        Formation clickedquiz =  table.getSelectionModel().getSelectedItem();
        NomTextField.setText(clickedquiz.getNomFormation());
        DureeTextField.setText(String.valueOf(clickedquiz.getDureeFormation()));
        NiveauTextField.setText(String.valueOf(clickedquiz.getNiveauFormation()));
        nbJoursTextField.setText(String.valueOf(clickedquiz.getNbJours()));
    }

    @FXML
    void modifier(ActionEvent event) {
        Formation clickedFormation = table.getSelectionModel().getSelectedItem();

        if (clickedFormation == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Veuillez sélectionner une formation à modifier.");
            alert.show();
        } else {

            String nom, duree, niveau;
            int nbJours;
            int index = table.getSelectionModel().getSelectedIndex();
            int idd = Integer.parseInt(String.valueOf(table.getItems().get(index).getIdFormation()));
            nom = NomTextField.getText();
            duree = DureeTextField.getText();
            niveau = NiveauTextField.getText();
            nbJours = Integer.valueOf(nbJoursTextField.getText());


            boolean modifications = !nom.equals(table.getItems().get(index).getNomFormation())
                    || !duree.equals(table.getItems().get(index).getDureeFormation())
                    || !niveau.equals(table.getItems().get(index).getNiveauFormation())
                    || nbJours != table.getItems().get(index).getNbJours();

            if (modifications) {
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation de modification");
                confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir modifier cette formation ?");
                confirmationDialog.setContentText("Cliquez sur OK pour confirmer la modification, ou sur Annuler pour annuler l'opération.");
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    String requete = "UPDATE formation SET nomFormation=?, dureeFormation=?,niveauFormation=?,nbJours=? WHERE idFormation=?";
                    try {
                        PreparedStatement pst = Myconnection.getInstance().getCnx().prepareStatement(requete);
                        pst.setString(1, nom);
                        pst.setString(2, duree);
                        pst.setString(3, niveau);
                        pst.setInt(4, nbJours);
                        pst.setInt(5, idd);

                        int rowsAffected = pst.executeUpdate();
                        afficherFormation();

                        if (rowsAffected > 0) {
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setContentText("La formation a été modifiée avec succès");
                            successAlert.show();
                        } else {
                            System.out.println("Aucune formation trouvée avec l'ID " + idd + " pour la mise à jour");
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La modification a été annulée");
                    alert.show();
                }
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Aucune modification n'a été apportée");
                alert.show();
            }
        }



    }

    @FXML
    void supprimer(ActionEvent event) {
        Formation clickedFormation = table.getSelectionModel().getSelectedItem();

        if (clickedFormation == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner une formation à supprimer.");
            alert.show();
        } else {

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir supprimer cette formation ?");
            confirmationDialog.setContentText("Cliquez sur OK pour confirmer la suppression, ou sur Annuler pour annuler l'opération.");


            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                table.getItems().remove(clickedFormation);
                FormationServices formationServices = new FormationServices();
                formationServices.deleteEntity(clickedFormation);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("La formation a été supprimée avec succès");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("La suppression a ete annulée");
                alert.show();
            }
        }

    }



    @FXML
    void initialize() {
      afficherFormation();
    }

    public void back(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//
//
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Afficher Formations");
        stage.setScene(scene);
        stage.show();
    }
}

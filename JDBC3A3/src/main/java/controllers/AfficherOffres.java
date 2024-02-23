package controllers;

import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.OffreServices;
import utils.MyConnection;

public class AfficherOffres implements Initializable  {
    @FXML
    private TableView<Offre> tabview ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Offre, String> coldesc;

    @FXML
    private TableColumn<Offre, Integer> colid = new TableColumn<>();

    @FXML
    private TableColumn<Offre, String> colnom;

    @FXML
    private TableColumn<Offre, Float> colprix;

    @FXML
    private TableColumn<Offre, String> colstat;


    @FXML
    private TextField descup;

    @FXML
    private TextField nomup;

    @FXML
    private TextField prixup;

    @FXML
    private ChoiceBox<String> dropup;

    @FXML
    private Button updatebtn;

    @FXML
    private VBox vb;
    @FXML
    private Button ajouterbtn;

    List<Offre> listOffre;
    int id;
Connection connection = null;
int index=-1;
    String[] statut = {"Disponible","Non disponible"};


@Override
    public void initialize(URL url, ResourceBundle resources) {
    afficherOffre();
    dropup.getItems().addAll(statut);
}

public void afficherOffre(){
    OffreServices offreServices = new OffreServices();
    colid.setCellValueFactory(new PropertyValueFactory<>("id"));
    colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    colstat.setCellValueFactory(new PropertyValueFactory<>("statut"));
    coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    tabview.setItems(offreServices.getAllData());
}

    @FXML
    void updateOffre(ActionEvent event) {
        String name, statut,description;
        Float price;
        index= tabview.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Sélectionner un offre à modifier d'abord .");
            alert.show();
            return;
        }
        id = Integer.parseInt(String.valueOf(tabview.getItems().get(index).getId()));
        name=nomup.getText();
        statut = dropup.getValue() != null ? dropup.getValue().toString() : "";
        if (statut.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a status.");
            alert.show();
            return;
        }
        description=descup.getText();
        price=Float.valueOf(prixup.getText());

        String requete = "UPDATE offre SET nom=?, prix=?,statut=?, description=?  WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, name);
            pst.setFloat(2,price);
            pst.setString(3, statut);
            pst.setString(4, description);
            pst.setInt(5,id);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> choice = alert.showAndWait();
            alert.setContentText("Voulez-vous vraiment modifier l'offre N"+id);

            if (choice.get() == ButtonType.OK){


                int rowsAffected = pst.executeUpdate();
                afficherOffre();

                if (rowsAffected > 0) {
                    System.out.println("Offre with ID " + id + " updated successfully");
                } else {
                    System.out.println("No Offre found with ID " + id+ " for updating");
                }
            }
        }//DU TRY
        catch (SQLException e) {
                System.out.println(e.getMessage());
            }





            }







    public void rowClicked(javafx.scene.input.MouseEvent mouseEvent) {
        index = tabview.getSelectionModel().getSelectedIndex();
        Offre clickedoffre =  tabview.getSelectionModel().getSelectedItem();
        nomup.setText(clickedoffre.getNom());
        prixup.setText(String.valueOf(clickedoffre.getPrix()));
        dropup.setValue(clickedoffre.getStatut());
        descup.setText(clickedoffre.getDescription());
    }

    public void onDelete(ActionEvent actionEvent) {
        Offre clickedoffre =  tabview.getSelectionModel().getSelectedItem();
        if (index == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Choisisez un offre à supprimer. ");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Voulez-vous vraiment supprimer l'offre N"+clickedoffre.getId());
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK) {

            tabview.getItems().remove(clickedoffre); // Remove the offer from the TableView
            OffreServices offreServices = new OffreServices();

            offreServices.deleteEntity(clickedoffre);
        }

    }

    public void addOffre(ActionEvent actionEvent) {
        Offre offre = new Offre(nomup.getText(),dropup.getValue().toString(),descup.getText(),
                Float.valueOf(prixup.getText()));
        index= tabview.getSelectionModel().getSelectedIndex();

        // Check if any field is null or empty
        if (nomup.getText().isEmpty() || descup.getText().isEmpty() || prixup.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Remplir les champs d'abord");
            alert.show();
            return;
        }





        // Check for empty or null values before parsing to Float
        Float price = null;
        if (!prixup.getText().isEmpty()) {
            try {
                price = Float.valueOf(prixup.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Veuillez saisir un prix valide.");
                alert.show();
                return;
            }
        }
        OffreServices offreServices = new OffreServices();
        String name = nomup.getText();
        String statut = dropup.getValue() != null ? dropup.getValue().toString() : "";
        String description = descup.getText();
        //Float price = Float.valueOf(prixup.getText());
        if (isOfferExists(name, statut, description, price)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("This offer already exists.");
            alert.show();
            return;
        }
        offreServices.addEntity2(offre);
        afficherOffre();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'offre est ajoutée avec succés");
        alert.show();
    }

    private boolean isOfferExists(String name, String statut, String description, Float price) {
        OffreServices offreServices = new OffreServices();
        List<Offre> existingOffers = offreServices.getAllData();

        for (Offre existingOffer : existingOffers) {
            if (existingOffer.getNom().equals(name)
                    && existingOffer.getStatut().equals(statut)
                    && existingOffer.getDescription().equals(description)
                    && existingOffer.getPrix()==price) {
                return true; // Offer with the same details already exists
            }
        }

        return false; // Offer is unique
    }
    }




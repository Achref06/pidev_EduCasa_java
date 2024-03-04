package controles;

import entities.Form;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.FormServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Form, String> date;

    @FXML
    private TableColumn<Form, Integer> id;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Form> searchForm;

    @FXML
    private TableColumn<Form, String> statut;

    @FXML
    private Button formulaire;

    @FXML
    void searchForm(ActionEvent event) {
        String searchText = searchTextField.getText().trim();

        // Call the search method from the service class
        FormServices formServices = new FormServices();
        List<Form> searchResults = formServices.searchForm(searchText);

        // Display the search results in the table view
        ObservableList<Form> observableList = FXCollections.observableList(searchResults);
        searchForm.setItems(observableList);
    }

    @FXML
    void afficherFormulaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowForm.fxml"));
            Parent nextInterfaceParent = loader.load();
            Scene nextInterfaceScene = new Scene(nextInterfaceParent);

            // Get the stage
            Stage stage = (Stage) formulaire.getScene().getWindow();

            // Set the new scene
            stage.setScene(nextInterfaceScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        final FormServices fs=new FormServices();
        List<Form> forms= fs.getAllData();
        ObservableList<Form> observableList= FXCollections.observableList(forms);
        searchForm.setItems(observableList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
    }

}

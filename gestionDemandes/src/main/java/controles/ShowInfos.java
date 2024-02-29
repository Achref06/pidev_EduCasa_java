package controles;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Infos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.InfosServices;

public class ShowInfos {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Infos, String> dateN;

    @FXML
    private TableColumn<Infos, String> diplome;

    @FXML
    private TableColumn<Infos, String> email;

    @FXML
    private TableColumn<Infos, String> experience;

    @FXML
    private Button formulaire;

    @FXML
    private TableColumn<Infos, Integer> idF;

    @FXML
    private TableColumn<Infos, Integer> idP;

    @FXML
    private TableColumn<Infos, String> matiere;

    @FXML
    private TableColumn<Infos, String> motivation;

    @FXML
    private TableColumn<Infos, String> nom;

    @FXML
    private TableColumn<Infos, String> prenom;

    @FXML
    private TableView<Infos> showDonnees;

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
        final InfosServices is =new InfosServices();
        List<Infos> infos = is.getAllData();
        ObservableList<Infos> observableList= FXCollections.observableList(infos);
        showDonnees.setItems(observableList);
        idP.setCellValueFactory(new PropertyValueFactory<>("idP"));
        idF.setCellValueFactory(new PropertyValueFactory<>("idF"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateN.setCellValueFactory(new PropertyValueFactory<>("dateN"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        motivation.setCellValueFactory(new PropertyValueFactory<>("motivation"));
        matiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        diplome.setCellValueFactory(new PropertyValueFactory<>("diplome"));
        diplome.setCellFactory(column -> {
            return new TableCell<Infos, String>() {
                final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        // Load the image and set it to the ImageView
                        Image image = new Image(new File(item).toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(100); // Set width as needed
                        imageView.setFitHeight(100); // Set height as needed
                        setGraphic(imageView);
                    }
                }
            };
        });
    }

}

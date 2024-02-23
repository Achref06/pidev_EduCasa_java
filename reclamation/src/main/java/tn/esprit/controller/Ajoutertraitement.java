

package tn.esprit.controller;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.ResourceBundle;

        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;


        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.event.ActionEvent;

        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;
        import tn.esprit.entities.reclamation;
        import tn.esprit.entities.traitement;
        import tn.esprit.services.reclamationService;
        import tn.esprit.services.traitementServices;

        import javafx.scene.control.Alert;

public class Ajoutertraitement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField idTadd;


    @FXML
    private TextField numRadd;

    @FXML
    private TextField reponseadd;

    @FXML
    private Button ajoutertraitement;


    private final traitementServices ts=new traitementServices();
    private tn.esprit.entities.traitement traitement;

    @FXML
    void handleajoutraitbuton(ActionEvent event) throws IOException {
        reclamation reclamation = new reclamation(idTadd.getText(),  numRadd.getText(), reponseadd.getText());
        reclamationService reclamationService = new reclamationService();
        try {
            ts.addT(traitement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("traitement ajoutee ");
        alert.show();

        Parent root = FXMLLoader.load(getClass().getResource("/showtraitement.fxml"));
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène pour afficher la deuxième interface
        stage.setScene(scene);
        stage.show();

    }

   /* void ajoutertraitement(ActionEvent event) {
        traitement traitement = new traitement(idTadd.getText(), numRadd.getText(), reponseadd.getText());
        traitementServices traitementServices = new traitementServices();
        try {
            traitementServices.addT(traitement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("traitement ajoutee ");
        alert.show();


    }*/
    @FXML
    void initialize() {
        /*try {
            List<traitement> traitements= ts.displayList();
            ObservableList<traitement> observableList= FXCollections.observableList(traitements);
            showtraitment.setItems(observableList);
            idTshow.setCellValueFactory(new PropertyValueFactory<>("idT"));
            numRshow.setCellValueFactory(new PropertyValueFactory<>("numR"));
            reponseShow.setCellValueFactory(new PropertyValueFactory<>("reponse"));

        }catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText( e.getMessage());
            alert.showAndWait();
        }*/
    }

    }


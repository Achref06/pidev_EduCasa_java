package controller;


import entities.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.UserServices;
import utils.MyConnection;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import controller.Profile;

public class UpdateUser {


    ObservableList<String> specialites = FXCollections.observableArrayList("Professeur", "Etudiant");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField mdpTextField;

    @FXML
    private TextField confirmTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private ToggleButton show1;

    @FXML
    private ToggleButton show2;

    @FXML
    private TextField niveauTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private ChoiceBox roleTextField;

    @FXML
    private TextField specialiteTextField;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Label showPassword1;

    @FXML
    private Label showPassword2;

    //private User user;

    //public UpdateUser(User user) {
      //  this.user = user;
    //}




    @FXML
    void updateUser(ActionEvent event) {


        if(nomTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || prenomTextField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        }
        else {

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
            String name, surname,em,ro,spec,niv,d;
            name=nomTextField.getText();
            d=mdpTextField.getText();
            surname=prenomTextField.getText();
            em=emailTextField.getText();
            ro=roleTextField.getValue().toString();
            spec=specialiteTextField.getText();
            niv=niveauTextField.getText();

        if (mdpTextField.getText().equals(confirmTextField.getText())) {
            confirmPasswordLabel.setText("");
            if (validateEmail(emailTextField.getText())) {

                String requete = "UPDATE user SET nom=?, prenom=?,mdp=?, role=?, specialite=?, niveau=?  WHERE email=?";
                try {
                    PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                    pst.setString(1, name);
                    pst.setString(2, surname);
                    pst.setString(3, d);
                    pst.setString(4, ro); // Use the selected value from RoleUpdate instead of "Professeur"
                    pst.setString(5, spec);
                    pst.setString(6, niv);
                    pst.setString(7, em);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("User updated successfully");

                    } else {
                        System.out.println("No User found for updating");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("L'utilisateur a été modifié avec succés");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Adresse email invalide");
                alert.show();
            }
        } else {
            confirmPasswordLabel.setText("Password does not match!");
        }
    }}}

    private boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

        /*
        nomTextField.setText(user.getNom());
        prenomTextField.setText(user.getPrenom());
        emailTextField.setText(user.getEmail());
        specialiteTextField.setText(user.getSpecialite());
        niveauTextField.setText(user.getNiveau());
         */
        showPassword1.setVisible(false);
        showPassword2.setVisible(false);

        roleTextField.setItems(specialites);
        roleTextField.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Check the new value of the roleTextField
            if ("Professeur".equals(newValue)) {
                niveauTextField.setEditable(false);
                specialiteTextField.setEditable(true);
                niveauTextField.setStyle("-fx-background-color: #FF0000;");
                specialiteTextField.setStyle("-fx-background-color: #FFFFFF");
            } else if ("Etudiant".equals(newValue)) {
                niveauTextField.setEditable(true);
                specialiteTextField.setEditable(false);
                specialiteTextField.setStyle("-fx-background-color: #FF0000;");
                niveauTextField.setStyle("-fx-background-color: #FFFFFF;");
            }
        });


    }

    public void passwordFieldKeyTyped(javafx.scene.input.KeyEvent keyEvent) {
        showPassword1.textProperty().bind(Bindings.concat(mdpTextField.getText()));
    }

    public void passwordFieldKeyTyped2(javafx.scene.input.KeyEvent keyEvent) {
        showPassword2.textProperty().bind(Bindings.concat(confirmTextField.getText()));
    }

    public void show1(ActionEvent actionEvent) {
        if (show1.isSelected()) {
            showPassword1.setVisible(true);
            showPassword1.textProperty().bind(Bindings.concat(mdpTextField.getText()));
            show1.setText("Hide");
        } else {
            showPassword1.setVisible(false);
            show1.setText("Show");
        }
    }

    public void show2(ActionEvent actionEvent) {
        if (show2.isSelected()) {
            showPassword2.setVisible(true);
            showPassword2.textProperty().bind(Bindings.concat(confirmTextField.getText()));
            show2.setText("Hide");
        } else {
            showPassword2.setVisible(false);
            show2.setText("Show");
        }
    }

}

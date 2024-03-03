package controller;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.UserServices;

import javax.mail.MessagingException;

public class AddUser {

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
    private ProgressBar passwordProgressBar;

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

    private User userToUpdate;


    @FXML
    void AjouterUser(ActionEvent event) {
        if (nomTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || prenomTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        } else {
            User user = new User(nomTextField.getText(), prenomTextField.getText(), mdpTextField.getText(),
                    emailTextField.getText(), roleTextField.getValue().toString(), specialiteTextField.getText(),
                    niveauTextField.getText());
            String password = mdpTextField.getText();
            if (calculatePasswordStrength(password) < 4) {
                showAlert("Weak Password", "Password must include at least one symbol, one uppercase character, and one number.");
                return;
            }

            if (mdpTextField.getText().equals(confirmTextField.getText())) {
                if (validateEmail(emailTextField.getText())) {
                    // Adjusted to generate the verification code here and pass both user and code to the verification interface
                    String verificationCode = generateVerificationCode();
                    // Directly call to send email with the generated code
                    registerUser(emailTextField.getText(), verificationCode);
                    // Correctly opening the verification interface with user and code
                    openVerificationInterface(user, verificationCode);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Adresse email invalide");
                    alert.show();
                }
            } else {
                confirmPasswordLabel.setText("Password does not match!");
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


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
        showPassword1.setVisible(false);
        showPassword2.setVisible(false);
        mdpTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updatePasswordStrength(newValue);
        });



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

    private void updatePasswordStrength(String password) {
        int strength = calculatePasswordStrength(password);
        passwordProgressBar.setProgress(strength / 4.0); // Assuming 4 is the max score
        // Adjust the color of the progress bar based on the strength
        passwordProgressBar.setStyle("-fx-accent: " + (strength < 2 ? "red" : strength < 3 ? "orange" : "green"));
    }

    private int calculatePasswordStrength(String password) {
        int strengthPoints = 0;
        if (password.length() > 8) strengthPoints++;
        if (password.matches("(?=.*[0-9]).*")) strengthPoints++;
        if (password.matches("(?=.*[a-z]).*")) strengthPoints++;
        if (password.matches("(?=.*[A-Z]).*")) strengthPoints++;
        if (password.matches("(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).*")) strengthPoints++;
        return Math.min(strengthPoints, 4);
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

    private String generateVerificationCode() {
        int length = 6; // Length of the verification code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public void registerUser(String email, String verificationCode) {
        EmailService emailService = new EmailService("achrefsaadaoui28@gmail.com", "mwdl ipnd nupm lkjo");
        try {
            emailService.sendEmail(email, "Email Verification", "Your verification code is: " + verificationCode);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void openVerificationInterface(User user, String verificationCode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/verif.fxml")); // Make sure the path is correct
            Parent root = loader.load();

            Verif verifController = loader.getController();
            verifController.setTemporaryUserData(user);  // Pass the user data
            verifController.setVerificationCode(verificationCode);  // Pass the verification code

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
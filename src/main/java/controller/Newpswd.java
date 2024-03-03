package controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.MyConnection;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Newpswd {
    @FXML
    private PasswordField confirmTextField;
    @FXML
    private PasswordField pswdTextField;
    @FXML
    private Button submitButton;

    @FXML
    private ProgressBar passwordProgressBar;

    @FXML
    private ToggleButton show1;

    @FXML
    private ToggleButton show2;

    @FXML
    private Label showPassword1;

    @FXML
    private Label showPassword2;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField emailField; // TextField to display the email to which the code was sent

    public static String userEmail; // Static variable to hold user email

    @FXML
    void initialize() {
        pswdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updatePasswordStrength(newValue);
        });
        showPassword1.setVisible(false);
        showPassword2.setVisible(false);
        submitButton.setOnAction(event -> {
            String password = pswdTextField.getText();
            String confirmPassword = confirmTextField.getText();

            if (!password.equals(confirmPassword)) {
                showAlert("Password Mismatch", "The passwords do not match. Please try again.");
                return;
            }

            int strength = calculatePasswordStrength(password);
            if (strength < 4) { // Assuming 4 is the max score indicating a strong password
                showAlert("Weak Password", "Your password must include at least one symbol, one uppercase letter, and one number.");
                return;
            }

            if (updateUserPassword(userEmail, password)) {
                showAlert("Success", "Your password has been successfully updated.");
                // Here, instead of navigating to another scene, restart the application
                restartApplication();
            } else {
                showAlert("Update Failed", "There was a problem updating your password. Please try again.");
            }
        });
    }

    private boolean updateUserPassword(String email, String newPassword) {
        try (Connection conn = MyConnection.getInstance().getCnx()) {
            String sql = "UPDATE user SET mdp = ? WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, email);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void restartApplication() {
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(Home.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            /* Build command: java -jar application.jar */
            final List<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            System.exit(0);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            // Handle exceptions here, such as showing an error message to the user.
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static void setUserEmail(String email) {
        userEmail = email;
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void passwordFieldKeyTyped(javafx.scene.input.KeyEvent keyEvent) {
        showPassword1.textProperty().bind(Bindings.concat(pswdTextField.getText()));
    }

    public void passwordFieldKeyTyped2(javafx.scene.input.KeyEvent keyEvent) {
        showPassword2.textProperty().bind(Bindings.concat(confirmTextField.getText()));
    }

    public void show1(ActionEvent actionEvent) {
        if (show1.isSelected()) {
            showPassword1.setVisible(true);
            showPassword1.textProperty().bind(Bindings.concat(pswdTextField.getText()));
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

    private void updatePasswordStrength(String password) {
        int strength = calculatePasswordStrength(password);
        passwordProgressBar.setProgress(strength / 4.0); // Assuming 4 is the max score

        // Change color based on strength
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

}






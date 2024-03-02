package controller;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import services.UserServices;

import java.security.SecureRandom;

public class Verif {

    @FXML
    private TextField verificationCodeField;

    private String expectedVerificationCode;

    @FXML
    private Button cancelButton;

    private User temporaryUserData;

    private static final String DATA_FOR_RANDOM_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static SecureRandom random = new SecureRandom();

    private String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }

    public void setVerificationCode(String code) {
        this.expectedVerificationCode = code;
    }

    public void setTemporaryUserData(User userData) {
        this.temporaryUserData = userData;
    }

    @FXML
    protected void onVerifyButtonClick() {
        String enteredCode = verificationCodeField.getText();
        if (enteredCode.equals(expectedVerificationCode)) {

            registerUser(temporaryUserData);
            showAlert("Verification Successful", "Your account has been successfully created!", Alert.AlertType.INFORMATION);
        } else {

            showAlert("Verification Failed", "The entered code is incorrect. Please try again.", Alert.AlertType.ERROR);
        }
    }


    private void registerUser(User user) {
        // Implement the logic to add the user to the database or your application's user management system here
        UserServices userServices = new UserServices();
        userServices.addEntity(user); // Assuming addEntity is the method to add the user
    }



    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cancelButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
}
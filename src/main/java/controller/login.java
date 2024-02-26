package controller;

import entities.User;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.stage.StageStyle;
import services.UserServices;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import utils.MyConnection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class login{

    public static String UserConnected;

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;


    @FXML
    private Hyperlink registerLink;

    public void initialize() {
        registerLink.setOnAction(event -> createAccountForm());
    }


    public void loginButtonOnAction(ActionEvent event){
        if(emailTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            loginMessageLabel.setText("You try to login");
            validateLogin();
        }else {
            loginMessageLabel.setText("Please enter email and password");
        }

    }

    public void cancelButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void noAccount(ActionEvent event)
    {
        createAccountForm();
    }


    public void validateLogin(){
        String verifyLogin= "Select count(1) FROM user where email = '" + emailTextField.getText() + "' And mdp = '" + passwordTextField.getText() + "'";

        try{

            Statement statement = MyConnection.getInstance().getCnx().createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("Congratulations");
                    UserConnected = emailTextField.getText();
                    Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
                    Stage registerStage = new Stage();
                    registerStage.initStyle(StageStyle.UNDECORATED);
                    registerStage.setScene(new Scene(root));
                    registerStage.show();



                }else{
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AddUser.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

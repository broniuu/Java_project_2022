package com.example.java_project_2022;

import databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import windowCreators.SnackBarCreator;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public TextField passwordBox;
    public TextField loginBox;
    public Button loginButton;
    public VBox rootPane;
    String incorrectLogin="Incorrect login or password";
    public void Login(ActionEvent event) throws IOException, SQLException {
        UserJdbcHelper userJdbcHelper = new UserJdbcHelper();
        if (userJdbcHelper.checkUser(loginBox.getText(), passwordBox.getText())) {
            User currentUser = null;

            for (User user : userJdbcHelper.getUsers()) {

                if (loginBox.getText().equals(user.getLogin())) {
                    currentUser = new User(user);
                }
            }


                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("restaurants.fxml"));
                Parent root = fxmlLoader.load();

                RestaurantsController restaurantsController = fxmlLoader.getController();
                restaurantsController.iniCurrentUser(currentUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1070, 560);
                stage.setScene(scene);
                stage.show();
            }
        else{
            SnackBarCreator.showSnackBar(rootPane,incorrectLogin);
        }
    }


    public void gotoRegister(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1070, 560);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
package com.example.java_project_2022;

import databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public TextField passwordBox;
    public TextField loginBox;
    public Button loginButton;

    public void Login(ActionEvent event) throws IOException, SQLException {
        UserJdbcHelper userJdbcHelper=new UserJdbcHelper();
       if(userJdbcHelper.checkUser(loginBox.getText(),passwordBox.getText())) {
           int id= userJdbcHelper.getId(loginBox.getText(),passwordBox.getText());
           User currentUser=null;
           for (User user:userJdbcHelper.getUsers()) {
               if(loginBox.getText().equals(user.getLogin())){
                   currentUser=user;
                   break;
               }
           }

           FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("restaurants.fxml"));
           Parent root=fxmlLoader.load();

           RestaurantsController restaurantsController =fxmlLoader.getController();
           restaurantsController.iniCurrentUser(currentUser);

           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           Scene scene=new Scene(root,900,500);
           stage.setScene(scene);
           stage.show();
       }
    }
    public void registerButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load() ,900, 540);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
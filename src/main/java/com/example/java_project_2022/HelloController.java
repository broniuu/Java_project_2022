package com.example.java_project_2022;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    public TextField passwordBox;
    public TextField loginBox;
    public Button loginButton;


    public void Login(ActionEvent event) throws IOException {
        String li="";
        li=li+loginBox.getText()+":";
        li=li+passwordBox.getText();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("restaurants.fxml"));
        Scene scene = new Scene(fxmlLoader.load() ,520, 540);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(li);
        stage.setScene(scene);
        stage.show();
    }

    public void registerButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load() ,520, 540);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
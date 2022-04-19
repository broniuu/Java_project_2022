package com.example.java_project_2022;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.CurrentUser;

import java.io.IOException;

public class MenuController {
    public Label currentNameLabel;
    public CurrentUser currentUser;
    public void toSettings(ActionEvent event) {

    }
    public void iniCurrentUser(CurrentUser currentUser){
        this.currentUser=currentUser;
        currentNameLabel.setText(this.currentUser.getLogin());
    }
    public void toRestaurants(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("restaurants.fxml"));
        Parent root=fxmlLoader.load();

        RestaurantsController restaurantsController =fxmlLoader.getController();
        restaurantsController.iniCurrentUser(currentUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);


        stage.setScene(scene);
        stage.show();
    }

    public void toSummary(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("summary.fxml"));
        Parent root=fxmlLoader.load();

        Summary summaryController =fxmlLoader.getController();
        summaryController.iniCurrentUser(currentUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);


        stage.setScene(scene);
        stage.show();
    }

    public void logOff(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Parent root=fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);


        stage.setScene(scene);
        stage.show();
    }

}

package com.example.java_project_2022;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.CurrentUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Summary implements Initializable {
    public Pane topSummaryPane;
    CurrentUser currentUser;
    MenuController menuController;
    public void iniCurrentUser(CurrentUser currentUser){
        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return menuController = new MenuController();
            }
        });
        Node view = null;
        try {
            view = (Node) fxmlLoader.load();

        } catch (IOException ex) {
        }

        topSummaryPane.getChildren().add(view);
    }
}

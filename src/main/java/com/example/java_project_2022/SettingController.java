package com.example.java_project_2022;

import databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.java_project_2022.Dictionary.deleteAccount;
import static windowCreators.CrateSettingsWindows.*;

public class SettingController implements Initializable {
    public HBox topPane;
    public VBox mainPane;
    MenuController menuController;
    User currentUser;
    User newUser;
    UserJdbcHelper userJdbcHelper;
    public void iniCurrentUser(User currentUser){
        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        fxmlLoader.setControllerFactory(param -> menuController = new MenuController());
        Node view = null;
        try {
            view = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        topPane.getChildren().add(view);
    }

    public void changeLoginInfo(ActionEvent event) {
        if(userJdbcHelper==null)userJdbcHelper=new UserJdbcHelper();
        newUser=new User(currentUser);
        crateAccountInfoWindow(currentUser,newUser);
        updateCurrentUser();
    }

    public void deleteAccount(ActionEvent event) throws IOException {
        if(checkPassword(deleteAccount,currentUser)){
            if(userJdbcHelper==null)userJdbcHelper=new UserJdbcHelper();
            userJdbcHelper.deleteUser(currentUser);
            logOut(event);
        }
    }

    private void logOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root=fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,320,240 );
        stage.setScene(scene);
        stage.show();
    }

    public void updateCurrentUser(){
        if(userJdbcHelper==null)userJdbcHelper=new UserJdbcHelper();
        for (User user: userJdbcHelper.getUsers()) {
            if(user.getUserId()== currentUser.getUserId()){
                currentUser=new User(user);
                break;
            }
        }
    }

    public void changePaymentInfo(ActionEvent event) {
        newUser=new User(currentUser);
        cratePaymentInfoWindow(currentUser,newUser);
        updateCurrentUser();

    }
}

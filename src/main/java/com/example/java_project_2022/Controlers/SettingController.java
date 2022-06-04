package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import com.example.java_project_2022.databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.java_project_2022.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.java_project_2022.Controlers.GlobalStatics.deleteAccount;
import static com.example.java_project_2022.windowCreators.CrateSettingsWindows.*;


/**
 * The class Setting controller implements initializable
 * Klasa umozliwiajaca zmiane danych uzytkownika.
 */
public class SettingController implements Initializable {
    public HBox topPane;
    public VBox mainPane;
    MenuController menuController;
    User currentUser;
    User newUser;
    UserJdbcHelper userJdbcHelper;

    /**
     *
     * inicjalizuje obecnego uzytkownika
     *
     * @param currentUser  the current user
     */
    public void iniCurrentUser(User currentUser){

        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }

    @Override

/**
 *
 * Initialize
 *umiesza menu w obecnym widoku
 * @param url  the url
 * @param resourceBundle  the resource bundle
 */
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


    /**
     *
     * zmienia informacje logowania dla uzytkownika
     * jezeli spelnia one walidatory oraz urzytkownik poda obecne haslo
     *
     * @param event  the event
     */
    public void changeLoginInfo(ActionEvent event) {

        if(userJdbcHelper==null)userJdbcHelper=new UserJdbcHelper();
        newUser=new User(currentUser);
        crateAccountInfoWindow(currentUser,newUser);
        updateCurrentUser();
    }


    /**
     *
     * usuwa kotno uzytkownika jezeli uzytkownik poda dobre haslo
     *
     * @param event  the event
     * @throws   IOException
     */
    public void deleteAccount(ActionEvent event) throws IOException {

        if(checkPassword(deleteAccount,currentUser)){
            if(userJdbcHelper==null)userJdbcHelper=new UserJdbcHelper();
            userJdbcHelper.deleteUser(currentUser);
            logOut(event);
        }
    }


    /**
     *
     * wylogowywuje uzytkownika
     *
     * @param event  the event
     * @throws   IOException
     */
    private void logOut(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root=fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,320,240 );
        stage.setScene(scene);
        stage.show();
    }


    /**
     *
     * uaktualnia obecne dane dla zalogowanego uzytkownika
     *
     */
    public void updateCurrentUser(){

        if(userJdbcHelper==null)userJdbcHelper=new UserJdbcHelper();
        for (User user: userJdbcHelper.getUsers()) {
            if(user.getUserId()== currentUser.getUserId()){
                currentUser=new User(user);
                break;
            }
        }
    }


    /**
     *
     * tworzy popup umozliwiajacy zmiane informacji platniczych.
     *
     *
     * @param event  the event
     */
    public void changePaymentInfo(ActionEvent event) {

        newUser=new User(currentUser);
        cratePaymentInfoWindow(currentUser,newUser);
        updateCurrentUser();

    }
}

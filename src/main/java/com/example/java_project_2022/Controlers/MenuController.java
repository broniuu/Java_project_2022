package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import com.example.java_project_2022.model.User;
import java.io.IOException;

import static com.example.java_project_2022.Controlers.GlobalStatics.HEIGHT;
import static com.example.java_project_2022.Controlers.GlobalStatics.WIDTH;


/**
 * The class Menu controller klasa obslugujaca menu.
 */
public class MenuController {
    public Label currentNameLabel;
    public User currentUser;
    public ToolBar MenuBar;


    /**
     *
     * przenosi uzytkownika do ustawien
     *
     * @param event  the event
     * @throws   IOException
     */
    public void toSettings(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings.fxml"));
        Parent root=fxmlLoader.load();

        SettingController settingController =fxmlLoader.getController();
        settingController.iniCurrentUser(currentUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root,WIDTH,HEIGHT);

        stage.setScene(scene);
        stage.show();

    }

    /**
     *
     * inicjalizuje obecnego uzytkownika
     *
     * @param currentUser  the current user
     */
    public void iniCurrentUser(User currentUser){

        this.currentUser=currentUser;
        currentNameLabel.setText(this.currentUser.getLogin());
    }

    /**
     *
     * przechodzi do restauracji
     *
     * @param event  the event
     * @throws   IOException
     */
    public void toRestaurants(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("restaurants.fxml"));
        Parent root=fxmlLoader.load();

        RestaurantsController restaurantsController =fxmlLoader.getController();
        restaurantsController.iniCurrentUser(currentUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root,WIDTH,HEIGHT);
        stage.setScene(scene);
        stage.show();
    }


    /**
     *
     * przechodzi do koszyka
     *
     * @param event  the event
     * @throws   IOException
     */
    public void toSummary(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("summary.fxml"));
        Parent root=fxmlLoader.load();

        SummaryController summaryController =fxmlLoader.getController();
        summaryController.iniCurrentUser(currentUser);
        summaryController.iniSummary();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root,WIDTH,HEIGHT);


        stage.setScene(scene);
        stage.show();
    }


    /**
     *
     * wylogowuje urzytkownika, przenosi go do ekranu logowania
     *
     * @param event  the event
     * @throws   IOException
     */
    public void logOff(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),320,240 );

        Stage stage=(Stage) MenuBar.getScene().getWindow();
        stage.setScene(scene );
        stage.show();



    }

}

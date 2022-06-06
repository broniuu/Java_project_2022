package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import com.example.java_project_2022.databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.java_project_2022.model.User;
import com.example.java_project_2022.windowCreators.SnackBarCreator;

import java.io.IOException;
import java.sql.SQLException;


/**
 *klasa ta obsługuje okno loginu.
 * */
public class LoginController {
    String incorrectLoginOrPassword="Incorrect login or password";
    public TextField passwordBox;
    public TextField loginBox;
    public Button loginButton;
    static int HEIGHT =560;
    static int WIDTH =1100;
    public VBox rootPane;
    /**
     * Klasa ta umożliwia odpowiada na nacisniecie przycisku i sprawdza czy zalogowanie jest mozliwe.
     *
     * uzywamy obiektu klasy userJdbcHelper do sprawdzenia czy użytkownik istnieje jezeli tak pozyskujemy Tworzymy klase user
     * i przypisujemy do niej obecnego urzytkownika pobranego takze przy pomocy  userJdbcHelper.
     * Następnie przekazujemy obecnego urzytkownika przy pomocy funkcji obiektu klasy RestaurantController, iniCurrentUser().
     * jezeli użytkownik wpisał zle haslo lub uzytkownik o takim loginnie nie istnieje wyświetlamy snackBar
     * z odpowiendia informacja pobraną z klasy Dictionery.
     *
     *
     * @param event  the event
     * @throws   IOException
     * @throws  SQLException
     */
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
                Scene scene = new Scene(root, WIDTH, HEIGHT);

                stage.setScene(scene);
                stage.show();
            }
        else{

            SnackBarCreator.showSnackBar(rootPane,incorrectLoginOrPassword);
        }
    }

    /**
     * funkcja ta przenosi nas do okna rejestracji dla nowego użytkownika
     * Goto register
     *
     * @param event  the event
     * @throws   IOException
     */
    public void gotoRegister(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
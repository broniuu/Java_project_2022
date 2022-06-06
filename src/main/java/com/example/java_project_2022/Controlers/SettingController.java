package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import com.example.java_project_2022.databaseConnection.UserJdbcHelper;
import com.example.java_project_2022.model.User;
import com.example.java_project_2022.windowCreators.SnackBarCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.java_project_2022.service.Validators.*;


/**
 * The class Setting controller implements initializable
 * Klasa umozliwiajaca zmiane danych uzytkownika.
 */
public class SettingController implements Initializable {
    String deleteAccount="If you want To delete your account, enter your password";

    static String  enterToAcceptChanges="Enter your current password to accept changes";
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

    public boolean checkPassword(String textQuestion, User currentUser){
        Stage popupWindow =new Stage();
        VBox layout= new VBox(10);
        Label currentPassword=new Label(textQuestion);
        PasswordField currentPasswordWindow=new PasswordField();
        Button confirmButton = new Button("Confirm");
        final Boolean[] isCorrect = {false};
        confirmButton.setOnAction(event -> {
            if(currentPasswordWindow.getText().equals(currentUser.getPassword()))
                popupWindow.close();
            isCorrect[0] =true;
        });
        layout.getChildren().addAll(currentPassword,currentPasswordWindow,confirmButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStylesheets().add(getClass().getResource("/pop_up_style.css").toExternalForm());
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();

        return isCorrect[0];
    }
    public void cratePaymentInfoWindow(User currentUser,User newUser){
        String invalidCvv="Correct ccv format is xxx";
        String invalidCard="Debit Card is not valid";
        String expireDate="Correct expire date format is xx/xx";
        Stage popupWindow =new Stage();
        VBox layout= new VBox(10);
        Label currentName =new Label("Name");
        Label currentSurname =new Label("Surname");
        Label currentDebitCardNumber =new Label("DebitCard Number");
        Label currentDebitCardCvv =new Label("DebitCard Cvv");
        Label currentDebitExpireDate=new Label("DebitCard ExpireDate");
        Label currentAdress =new Label("Adress");

        TextField currentNameWindow=new TextField(currentUser.getName());
        TextField currentSurnameWindow =new TextField(currentUser.getSurname());
        TextField currentDebitCardWindow=new TextField(currentUser.getDebitCardNumber());
        TextField currentDebitCardCvvWindow=new TextField(currentUser.getCvv());
        TextField currentExpireDateWindow=new TextField(currentUser.getExpireDate());
        TextField currentAdressWindow=new TextField(currentUser.getAddress());

        Button saveButton= new Button("Save");
        saveButton.setOnAction(event -> {

            if(!debitCardValidator(currentDebitCardWindow.getText())){
                SnackBarCreator.showSnackBar(layout,invalidCard); return;}
            if(!cvvValidator(currentDebitCardCvvWindow.getText())){SnackBarCreator.showSnackBar(layout,invalidCvv); return;}
            if(!expireDateValidator(currentExpireDateWindow.getText())){SnackBarCreator.showSnackBar(layout,expireDate); return;}

            if(!currentSurnameWindow.getText().isEmpty()&&!currentNameWindow.getText().isEmpty()){
                newUser.setName(currentNameWindow.getText());
                newUser.setSurname(currentSurnameWindow.getText());
                newUser.setDebitCardNumber(currentDebitCardNumber.getText());
                newUser.setDebitCardNumber(currentDebitCardWindow.getText());
                newUser.setCvv(currentDebitCardCvvWindow.getText());
                newUser.setExpireDate(currentExpireDateWindow.getText());
            }
            if(checkPassword(enterToAcceptChanges,currentUser)){
                UserJdbcHelper userJdbcHelper;
                userJdbcHelper=new UserJdbcHelper();
                userJdbcHelper.updateUser(newUser);

                popupWindow.close();
            }

        });

        Button exitButton=new Button("Exit");
        exitButton.setOnAction(event -> popupWindow.close());
        layout.getChildren().addAll(currentName,currentNameWindow, currentSurname, currentSurnameWindow, currentDebitCardNumber,currentDebitCardWindow,
                currentDebitCardCvv,currentDebitCardCvvWindow,currentDebitExpireDate,currentExpireDateWindow,currentAdress,currentAdressWindow,
                saveButton,exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStylesheets().add(getClass().getResource("/pop_up_style.css").toExternalForm());
        Scene scene1= new Scene(layout, 300, 450);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
    public void crateAccountInfoWindow(User currentUser,User newUser) {
        String incorrectPassword="Your password needs to have at least \n1 big Letter, 1 number and 1 special character ";
        String invalidEmail="This Email Isn't valid";
        String invalidLogin="This Login Is already occupied ";
        UserJdbcHelper userJdbcHelper=new UserJdbcHelper();
        Stage popupWindow =new Stage();
        VBox layout= new VBox(10);
        Label currentLogin=new Label("Login");
        Label currentEmail=new Label("Email");
        Label currentPassword=new Label("Password");

        TextField currentLoginWindow=new TextField(currentUser.getLogin());
        TextField currentEmailWindow=new TextField(currentUser.getEmail());
        PasswordField currentPasswordWindow=new PasswordField();
        Button saveButton= new Button("Save");
        saveButton.setOnAction(event -> {
            if(!currentLoginWindow.getText().equals(currentUser.getLogin())){
                try {
                    if(!userJdbcHelper.isLoginFree(currentLoginWindow.getText())){SnackBarCreator.showSnackBar(layout,invalidLogin); return;}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(!emailValidator(currentEmailWindow.getText())){SnackBarCreator.showSnackBar(layout,invalidEmail); return;}
            if(!currentPasswordWindow.getText().isEmpty()){
                if(!passwordValidator(currentPasswordWindow.getText())){SnackBarCreator.showSnackBar(layout,incorrectPassword); return;}
            }
            if(!currentEmailWindow.getText().isEmpty()&&!currentLoginWindow.getText().isEmpty()){

                newUser.setLogin(currentLoginWindow.getText());
                newUser.setEmail(currentEmailWindow.getText());

                if(!currentPasswordWindow.getText().isEmpty()){
                    newUser.setPassword(currentPasswordWindow.getText());
                }
            }
            if(checkPassword(enterToAcceptChanges,currentUser)){
                UserJdbcHelper userJdbcHelper1;
                userJdbcHelper1 =new UserJdbcHelper();
                userJdbcHelper1.updateUser(newUser);

                popupWindow.close();
            }

        });
        Button exitButton=new Button("Exit");
        exitButton.setOnAction(event -> popupWindow.close());
        layout.getChildren().addAll(currentLogin,currentLoginWindow,currentEmail, currentEmailWindow,currentPassword,currentPasswordWindow,saveButton,exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStylesheets().add(getClass().getResource("/pop_up_style.css").toExternalForm());
        Scene scene1= new Scene(layout, 600, 350);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
}

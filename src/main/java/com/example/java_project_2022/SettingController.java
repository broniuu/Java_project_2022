package com.example.java_project_2022;

import databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Callback;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    public HBox topPane;
    public VBox mainPane;
    MenuController menuController;
    User currentUser;
    User newUser;
    String enterToAcceptChanges="Enter your current password to accept changes";
    UserJdbcHelper userJdbcHelper;
    public void iniCurrentUser(User currentUser){
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
        topPane.getChildren().add(view);
    }

    public void changeLoginInfo(ActionEvent event) {
        newUser=new User(currentUser);
        crateAccountInfoWindow();
    }
    public boolean checkPassword(String textQuestion){
        Stage popupWindow =new Stage();
        VBox layout= new VBox(10);
        Label currentPassword=new Label(textQuestion);
        PasswordField currentPasswordWindow=new PasswordField();
        Button confirmButton = new Button("Confirm");
        final Boolean[] isCorrect = {false};
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currentPasswordWindow.getText().equals(currentUser.getPassword()))
                popupWindow.close();
                isCorrect[0] =true;
            }
        });
        layout.getChildren().addAll(currentPassword,currentPasswordWindow,confirmButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
        System.out.println(isCorrect[0]);
        return isCorrect[0];
    }
    public void deleteAccount(ActionEvent event) throws IOException {
        if(checkPassword("If you want To delete your account, enter your password")){
            userJdbcHelper=new UserJdbcHelper();
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

    public void crateAccountInfoWindow() {
        Stage popupWindow =new Stage();
        VBox layout= new VBox(10);
        Label currentLogin=new Label("Login");
        Label currentEmail=new Label("Email");
        Label currentPassword=new Label("Password");

        TextField currentLoginWindow=new TextField(currentUser.getLogin());
        TextField currentEmailWindow=new TextField(currentUser.getEmail());
        PasswordField currentPasswordWindow=new PasswordField();
        Button saveButton= new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!currentEmailWindow.getText().isEmpty()&&!currentLoginWindow.getText().isEmpty()){

                    newUser.setLogin(currentLoginWindow.getText().toString());
                    newUser.setEmail(currentEmailWindow.getText().toString());

                    if(!currentPasswordWindow.getText().isEmpty()){
                        newUser.setPassword(currentPasswordWindow.getText().toString());
                    }
                }
                if(checkPassword(enterToAcceptChanges)){
                    userJdbcHelper=new UserJdbcHelper();
                    userJdbcHelper.updateUser(newUser);
                    updateCurrentUser();
                    popupWindow.close();
                }

            }
        });

        Button exitButton=new Button("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupWindow.close();

            }
        });
        layout.getChildren().addAll(currentLogin,currentLoginWindow,currentEmail, currentEmailWindow,currentPassword,currentPasswordWindow,saveButton,exitButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
    public void updateCurrentUser(){
        for (User user: userJdbcHelper.getUsers()) {
            if(user.getUserId()== currentUser.getUserId()){
                currentUser=new User(user);
                break;
            }
        }
    }
    public void cratePaymentInfoWindow() {
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
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!currentSurnameWindow.getText().isEmpty()&&!currentNameWindow.getText().isEmpty()){
                    newUser.setName(currentNameWindow.getText());
                    newUser.setSurname(currentSurnameWindow.getText());
                    newUser.setDebitCardNumber(currentDebitCardNumber.getText());
                    newUser.setDebitCardNumber(currentDebitCardWindow.getText());
                    newUser.setCvv(currentDebitCardCvvWindow.getText());
                    newUser.setExpireDate(currentExpireDateWindow.getText());
                }
                if(checkPassword(enterToAcceptChanges)){
                    userJdbcHelper=new UserJdbcHelper();
                    userJdbcHelper.updateUser(newUser);
                    updateCurrentUser();
                    popupWindow.close();
                }

            }
        });

        Button exitButton=new Button("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupWindow.close();

            }
        });
        layout.getChildren().addAll(currentName,currentNameWindow, currentSurname, currentSurnameWindow, currentDebitCardNumber,currentDebitCardWindow,
                currentDebitCardCvv,currentDebitCardCvvWindow,currentDebitExpireDate,currentExpireDateWindow,currentAdress,currentAdressWindow,
                saveButton,exitButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 450);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }

    public void changePaymentInfo(ActionEvent event) {
        newUser=new User(currentUser);
        cratePaymentInfoWindow();

    }
}

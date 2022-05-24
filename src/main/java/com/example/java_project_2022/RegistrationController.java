package com.example.java_project_2022;

import databaseConnection.UserJdbcHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

import static model.Validators.*;
import static windowCreators.SnackBarCreator.showSnackBar;

public class RegistrationController {

    public TextField RCardNumberBox;
    public TextField RCCVBBox;
    public TextField RExpirationDateBox;
    public ListView<String> RSummaryView;
    public TextField RNameBox;
    public TextField RSurnameBox;
    public TextField RStreetBox;
    public TextField RHomeNumberBox;
    public TextField RPostCodeBox;
    public TextField REmail;
    public TextField RLogin;
    public PasswordField RPassword;
    public PasswordField RReapeatPassword;
    public TextField RCity;
    public AnchorPane rootPane;
    String incorrectPassword="Your password needs to have at least 1 big Letter, 1 number and 1 special character ";
    String notIdenticalPasswords="Your Passwords are not identical ";
    String incorrectLogin ="this login already exists";
    String invalidEmail="This Email Isn't valid";
    String invalidCvv="Correct ccv format is xxx";
    String invalidPostCode="Correct postCode format is xx-xxx";
    String invalidCard="Debit Card is not valid";
    String expireDate="Correct expire date format is xx/xx";
    public void addToList(Event event) {
        RSummaryView.getItems().clear();
        String passes ="login: "+RLogin.getText()+"password "+RPassword.getText()+"Email:"+REmail.getText();
        String name ="Personals: "+RNameBox.getText()+" "+RSurnameBox.getText();
        String adress="Adress: "+RCity.getText()+","+RStreetBox.getText()+","+RHomeNumberBox.getText()+","+RPostCodeBox.getText();
        String cardInfo="CardNumber "+RCardNumberBox.getText()+" CCV: "+RCCVBBox.getText()+" Expiration Date: "+RExpirationDateBox.getText();
        if(RSummaryView.getItems().isEmpty()){
            RSummaryView.getItems().add(passes);
            RSummaryView.getItems().add(name);
            RSummaryView.getItems().add(adress);
            RSummaryView.getItems().add(cardInfo);
        }else{
            RSummaryView.getItems().removeAll(passes,name,adress,cardInfo);
            RSummaryView.getItems().add(passes);
            RSummaryView.getItems().add(name);
            RSummaryView.getItems().add(adress);
            RSummaryView.getItems().add(cardInfo);
        }
    }
    public void register(ActionEvent event) throws SQLException, IOException {
        String address=""+RCity.getText()+","+RStreetBox.getText()+","+RHomeNumberBox.getText()+","+RPostCodeBox.getText();


        UserJdbcHelper userJdbcHelper=new UserJdbcHelper();
        if(!passwordValidator(RPassword.getText())) {showSnackBar(rootPane,incorrectPassword);return;}
        if(!RPassword.getText().equals(RReapeatPassword.getText())){showSnackBar(rootPane,notIdenticalPasswords);return;}
        if(!userJdbcHelper.isLoginFree(RLogin.getText())){showSnackBar(rootPane,incorrectLogin);return;}
        if(!emailValidator(REmail.getText()) ){showSnackBar(rootPane,invalidEmail);return;}
        if(!cvvValidator(RCCVBBox.getText())){showSnackBar(rootPane,invalidCvv);return;}
        if(!postCodeValidator(RPostCodeBox.getText())){showSnackBar(rootPane,invalidPostCode);return;}
        if(!debitCardValidator(RCardNumberBox.getText())){showSnackBar(rootPane,invalidCard);return;}
        if(!expireDateValidator(RExpirationDateBox.getText())){showSnackBar(rootPane,expireDate);return;}

        if(RPostCodeBox.getText().isEmpty()&&RCCVBBox.getText().isEmpty()&&RStreetBox.getText().isEmpty()&&RHomeNumberBox.getText().isEmpty()
                &&RNameBox.getText().isEmpty()&&RSurnameBox.getText().isEmpty()&&RPassword.getText().isEmpty()&&RReapeatPassword.getText().isEmpty()&&RCardNumberBox.getText().isEmpty()&&
                RExpirationDateBox.getText().isEmpty())return;

        User user=new User(RLogin.getText(),RPassword.getText(),RNameBox.getText(),RSurnameBox.getText(),address,RCardNumberBox.getText(),RExpirationDateBox.getText(),RCCVBBox.getText(),REmail.getText());
        userJdbcHelper.addUser(user);
        gotoLogin(event);
    }

    private void gotoLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}

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
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegistrationController {

    public TextField RCardNumberBox;
    public TextField RCCVBBox;
    public TextField RExpirationDateBox;
    public ListView RSummaryView;
    public TextField RNameBox;
    public TextField RSurnameBox;
    public TextField RStreetBox;
    public TextField RHomeNumberBox;
    public TextField RPostCodeBox;
    public TextField REmail;
    public TextField RLogin;
    public PasswordField RPassword;
    public PasswordField RReapeatPassword;
    private Pattern validEmailPattern=Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    public void addToList(Event event) {
        String passes ="login: "+RLogin.getText()+",password "+RPassword.getText()+",Email: "+REmail.getText();
        String name ="Personals: "+RNameBox.getText()+" "+RSurnameBox.getText();
        String adress="Adress: "+RStreetBox.getText()+" "+RHomeNumberBox.getText()+" "+RPostCodeBox.getText();
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
        UserJdbcHelper userJdbcHelper=new UserJdbcHelper();
        if(!RPassword.getText().equals(RReapeatPassword.getText()))return;
        if(userJdbcHelper.checkUser(RLogin.getText(),RPassword.getText()))return;
        if(!emailValidation())return;
        if(RPostCodeBox.getText().isEmpty()&&RCCVBBox.getText().isEmpty()&&RStreetBox.getText().isEmpty()&&RHomeNumberBox.getText().isEmpty()
                &&RNameBox.getText().isEmpty()&&RSurnameBox.getText().isEmpty()&&RPassword.getText().isEmpty()&&RReapeatPassword.getText().isEmpty()&&RCardNumberBox.getText().isEmpty()&&
                RExpirationDateBox.getText().isEmpty())return;

        String adress=""+RStreetBox.getText()+" "+RHomeNumberBox.getText()+" "+RPostCodeBox.getText();
        User user=new User(RLogin.getText(),RPassword.getText(),RNameBox.getText(),RSurnameBox.getText(),adress,RCardNumberBox.getText(),RExpirationDateBox.getText(),RCCVBBox.getText(),REmail.getText());
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

    public boolean emailValidation(){
        String s= REmail.getText();
        if(validEmailPattern.matcher(s).matches()){
            return true;
        }
        else{
            return false;
        }
    }

}

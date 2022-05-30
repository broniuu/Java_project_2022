package windowCreators;

import databaseConnection.UserJdbcHelper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import java.sql.SQLException;

import static model.Validators.*;

public class CrateSettingsWindows {
    static String  enterToAcceptChanges="Enter your current password to accept changes";
    public static boolean checkPassword(String textQuestion, User currentUser){
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
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
        System.out.println(isCorrect[0]);
        return isCorrect[0];
    }
    public static void cratePaymentInfoWindow(User currentUser,User newUser){
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

            if(!debitCardValidator(currentDebitCardWindow.getText())){SnackBarCreator.showSnackBar(layout,invalidCard); return;}
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
        Scene scene1= new Scene(layout, 300, 450);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
    public static void crateAccountInfoWindow(User currentUser,User newUser) {
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
        Scene scene1= new Scene(layout, 600, 350);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Change Logins Information");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
}

package com.example.java_project_2022.windowCreators;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SummaryWindowCreator {
    static String emailNotification="We send Confirmation of purchase to your email";
    static String thankYouMessage="Thank you For Buying with Szama(n)!!";
    public static void thanksNote() {
        Stage popupwindow=new Stage();
        VBox layout= new VBox(10);
        Label textField=new Label(thankYouMessage+"\n"+emailNotification);

        Button closeWindow =new Button("Close Window");
        closeWindow.setOnAction(event -> popupwindow.close());
        layout.getChildren().addAll(textField, closeWindow);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

}

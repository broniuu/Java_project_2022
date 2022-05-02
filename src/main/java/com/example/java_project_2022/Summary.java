package com.example.java_project_2022;

import databaseConnection.CardItemJdbcHelper;
import databaseConnection.DishJdbcHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CartItem;
import model.CurrentUser;
import model.Dish;
import service.CartItemService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Summary implements Initializable {
    public Pane topSummaryPane;
    public ListView SummaryList;
    public Label priceLabel;
    public BorderPane BPane;
    public ChoiceBox deliveryBox;
    CurrentUser currentUser;
    List<CartItem> cartItems;
    String note="";
    MenuController menuController;
    private double deliveryFee;
    private double costOFDelivery=9.99;

    public void iniCurrentUser(CurrentUser currentUser){
        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }
    public  void clear(){
        SummaryList.getItems().clear();
    }
    public void iniSummary(){

        DishJdbcHelper dishJdbcHelper=new DishJdbcHelper();
        List<HBox> boxes =new ArrayList<>();
        cartItems= getItems();
        List<Dish> dishes =dishJdbcHelper.getDishes();
        CartItemService.connectCartItemsWithDishes(cartItems, dishes);
        for (CartItem item:cartItems) {
            boxes.add(newItemBox(item));
        }
        SummaryList.getItems().addAll(boxes);
        updatedPrice();
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
        topSummaryPane.getChildren().add(view);
        deliveryBox.getItems().add("Delivery");
        deliveryBox.getItems().add("Takeaway");
        
    }
    public List<CartItem> getItems(){
        CardItemJdbcHelper cardItemJdbcHelper = new CardItemJdbcHelper();
        return cardItemJdbcHelper.getCartItems(currentUser.getId());

    }
    public HBox newItemBox(CartItem cartItem){
        CardItemJdbcHelper cardItemJdbcHelper=new CardItemJdbcHelper();
        final int[] I = {cartItem.getCountOfDish()};
        FXMLLoader fxmlLoader=new FXMLLoader();
        HBox box =new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();

        Label quntity=new Label(""+I[0]);
        Button plus=new Button("+");
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                I[0]++;
                cartItem.setCountOfDish(I[0]);
                cardItemJdbcHelper.updateCartItem(cartItem);
                quntity.setText(""+(I[0]));
                priceLabel.setText(""+(cartItem.getDish().getPrice()*I[0])+"zl");
                updatedPrice();
            }
        });
        Button minus=new Button("-");
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(I[0] >0){
                    I[0]--;
                }
                cartItem.setCountOfDish(I[0]);
                cardItemJdbcHelper.updateCartItem(cartItem);
                quntity.setText(""+(I[0]));
                priceLabel.setText(""+(cartItem.getDish().getPrice()*I[0])+"zl");
                updatedPrice();
            }
        });
        Button delButton =new Button("delete");
        delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cardItemJdbcHelper.deleteCartItem( cartItem);
                clear();
                iniSummary();
            }
        });
        nameLabel.setText(cartItem.getDish().getName());
        priceLabel.setText(""+(cartItem.getDish().getPrice()*I[0]));
        box.getChildren().add(nameLabel);
        box.getChildren().add(priceLabel);
        box.getChildren().add(minus);
        box.getChildren().add(quntity);
        box.getChildren().add(plus);
        box.getChildren().add(delButton);

        Scene scene = new Scene(box,900, 540);
        return box;
    }
    public void updatedPrice() {
        double sum=0;
        for (CartItem item:cartItems) {
            sum+=(item.getDish().getPrice()*item.getCountOfDish());
        }
        priceLabel.setText(""+(sum+deliveryFee)+"zl");
    }
    public void Order(ActionEvent event) {
        //dodać drukowanie paragonu

        //oprożnić koszyk
        for (CartItem item: cartItems) {
            CardItemJdbcHelper cardItemJdbcHelper=new CardItemJdbcHelper();
            cardItemJdbcHelper.deleteCartItem(item);
        }
        //wyswietlic podziekowanie za zakup
        thanksnote();
        clear();
        iniSummary();
    }
    public void thanksnote() {
        Stage popupwindow=new Stage();
        VBox layout= new VBox(10);
        Label textField=new Label("Thank you For Buying with Szama(n)!!");

        Button closeWinow=new Button("Close Window");
        closeWinow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
            }
        });
        layout.getChildren().addAll(textField, closeWinow);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
    public void note(ActionEvent event) {
        Stage popupwindow=new Stage();
        VBox layout= new VBox(10);
        TextArea textField=new TextArea();
        if(!note.isEmpty()){
            textField.setText(note);
        }
        Button addNote=new Button("Add note");
        addNote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                note=textField.getText();
                popupwindow.close();
            }
        });
        layout.getChildren().addAll(textField, addNote);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add note for chief / Delivery service");
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public void Delivery(ActionEvent event) {
        if(deliveryBox.getValue().equals("Delivery")){
            deliveryFee=costOFDelivery;
        }
        else{
            deliveryFee=0;
        }
        updatedPrice();
    }
}

package com.example.java_project_2022;

import databaseConnection.CardItemJdbcHelper;
import databaseConnection.DishJdbcHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.CartItem;
import model.Dish;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class DishesController implements Initializable {
    public ListView dishesList;
    public List<Dish> dishes;
    public Pane topDishesPane;
    User currentUser;
    MenuController controller;
    public void iniCurrentUser(User currentUser){
        this.currentUser=currentUser;
        controller.iniCurrentUser(currentUser);
    }
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return controller = new MenuController();
            }
        });
        Node view = null;
        try {
            view = (Node) fxmlLoader.load();

        } catch (IOException ex) {
        }
        topDishesPane.getChildren().add(view);
    }
    public void iniDishes(List<Dish> dishes ){
        this.dishes=dishes;
        DishJdbcHelper dishJdbcHelper=new DishJdbcHelper();
        for (Dish d:dishes) {
            dishesList.getItems().add(newDishBox(d));
        }
    }
    public HBox newDishBox(Dish dish){
        final int[] Quantity = {0};
        FXMLLoader fxmlLoader=new FXMLLoader();
        HBox box =new HBox();

        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();
        Label quntityLabel =new Label("0");
        Button plus=new Button("+");
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Quantity[0]++;
                quntityLabel.setText(""+(Quantity[0]));
            }
        });
        Button minus=new Button("-");
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Quantity[0] >0){
                    Quantity[0]--;
                }
                quntityLabel.setText(""+(Quantity[0]));
            }
        });
        Button addButton=new Button();
        addButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if(Quantity[0] >0){
                    CartItem cartItem=new CartItem(currentUser.getUserId(),dish.getDishId(), Quantity[0]);
                    System.out.println(dish.getDishId());

                    CardItemJdbcHelper CIH=new CardItemJdbcHelper();
                    CIH.upsertCartItem(cartItem);
                    Quantity[0]=0;
                    quntityLabel.setText(""+(Quantity[0]));
                }
            }
        });
        addButton.setText("Add");
        nameLabel.setText(dish.getName());
        nameLabel.setMinSize(220,20);
        nameLabel.setMaxSize(220,20);
        priceLabel.setText(""+dish.getPrice());
        box.setAlignment(CENTER);
        box.getChildren().add(nameLabel);
        box.getChildren().add(priceLabel);

        box.getChildren().add(minus);
        box.getChildren().add(quntityLabel);
        box.getChildren().add(plus);

        box.getChildren().add(addButton);
        Scene scene = new Scene(box,900, 540);
        return box;
    }
}
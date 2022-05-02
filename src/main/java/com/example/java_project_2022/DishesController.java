package com.example.java_project_2022;

import databaseConnection.CardItemJdbcHelper;
import databaseConnection.DishJdbcHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CartItem;
import model.CurrentUser;
import model.Dish;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DishesController implements Initializable {
    public ListView dishesList;
    public List<Dish> dishes;
    public Pane topDishesPane;
    CurrentUser currentUser;
    MenuController controller;
    public void iniCurrentUser(CurrentUser currentUser){
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
        final int[] I = {0};
        FXMLLoader fxmlLoader=new FXMLLoader();
        HBox box =new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();
        Label quntity=new Label("0");
        Button plus=new Button("+");
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                I[0]++;
                quntity.setText(""+(I[0]));
            }
        });
        Button minus=new Button("-");
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(I[0] >0){
                    I[0]--;
                }
                quntity.setText(""+(I[0]));
            }
        });
        Button addButton=new Button();
        addButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if(I[0] >0){
                    CartItem cartItem=new CartItem(currentUser.getId(),dish.getDishId(), I[0]);
                    System.out.println(dish.getDishId());

                    CardItemJdbcHelper CIH=new CardItemJdbcHelper();
                    CIH.addCartItem(cartItem);
                }
            }
        });
        addButton.setText("Add");
        nameLabel.setText(dish.getName());
        priceLabel.setText(""+dish.getPrice());
        box.getChildren().add(nameLabel);
        box.getChildren().add(priceLabel);
        box.getChildren().add(minus);
        box.getChildren().add(quntity);
        box.getChildren().add(plus);
        box.getChildren().add(addButton);

        Scene scene = new Scene(box,900, 540);
        return box;
    }
}

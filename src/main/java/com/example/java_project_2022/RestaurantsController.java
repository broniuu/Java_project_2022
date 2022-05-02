package com.example.java_project_2022;

import databaseConnection.DishJdbcHelper;
import databaseConnection.RestaurantsJdbcHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Dish;
import model.Restaurant;
import service.RestaurantDishConnector;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RestaurantsController implements Initializable {
    public ListView restaurantsList;
    public Button plusButton;
    public Button minusButton;
    public Button addButton;
    public Label priceLabel;
    public Label nameLabel;
    public Label quantityLabel;
    public int quantity= 0;

    public void minus(ActionEvent event) {
        if(quantity>0){
            quantity--;
        }
        quantityLabel.setText(" "+quantity+" ");
    }

    public void plus(ActionEvent event) {
        if(quantity<99){
            quantity++;
        }
        quantityLabel.setText(" "+quantity+" ");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RestaurantsJdbcHelper restaurantsJdbcHelper =new RestaurantsJdbcHelper();
        DishJdbcHelper dishJdbcHelper=new DishJdbcHelper();
        List<Dish> dishes= dishJdbcHelper.getDishes();
        List<Restaurant> restaurants = restaurantsJdbcHelper.getRestaurants();

        RestaurantDishConnector restaurantDishConnector=new RestaurantDishConnector();
        RestaurantDishConnector.fillRestaurantsWithDishes(restaurants,dishes);

        for(int i=0;i<restaurants.size();i++){
            try {
                restaurantsList.getItems().add(newStage(restaurants.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void goToDishes(Event event,Restaurant restaurant) throws IOException {
        String idRestaurant=""+restaurant.getRestaurantId();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dishes.fxml"));
        Parent root=fxmlLoader.load();

        DishesController dishesController=fxmlLoader.getController();
        dishesController.iniDishes(restaurant.getDishes());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setUserData(idRestaurant);

        stage.setScene(scene);
        stage.show();
    }
    public HBox newStage(Restaurant restaurant) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader();
        HBox box =new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label starLabel =new Label();
        Button addButton=new Button();
        addButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    goToDishes(event,restaurant);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        addButton.setText("Go to");
        nameLabel.setText(restaurant.getName());
        starLabel.setText("2/5");
        box.getChildren().add(nameLabel);
        box.getChildren().add(starLabel);
        box.getChildren().add(addButton);

        Scene scene = new Scene(box,520, 540);
        return box;
    }
}

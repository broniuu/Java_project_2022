package com.example.java_project_2022;

import databaseConnection.DishJdbcHelper;
import databaseConnection.RestaurantJdbcHelper;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Dish;
import model.Restaurant;
import model.User;
import service.RestaurantDishConnector;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RestaurantsController implements Initializable {
   
    public Button plusButton;
    public Button minusButton;
    int numberOfRows=3;
    public Label priceLabel;
    public Label nameLabel;
    public Label quantityLabel;
    public int quantity= 0;
    public Pane topRestaurantsPane;
    public BorderPane BorderPaneRestaurants;
    public AnchorPane centerPane;
    User currentUser;
    MenuController controller;
    public void iniCurrentUser(User currentUser){
        this.currentUser=currentUser;
        controller.iniCurrentUser(currentUser);
    }

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
        RestaurantJdbcHelper restaurantsJdbcHelper = new RestaurantJdbcHelper();
        DishJdbcHelper dishJdbcHelper = new DishJdbcHelper();
        List<Dish> dishes = dishJdbcHelper.getDishes();
        List<Restaurant> restaurants = restaurantsJdbcHelper.getRestaurants();
        System.out.println(restaurants.get(2).getName()+"   "+restaurants.get(2).getImageUrl());
        GridPane restaurantLayout=new GridPane();
        restaurantLayout.setGridLinesVisible(true);
        RestaurantDishConnector restaurantDishConnector = new RestaurantDishConnector();
        RestaurantDishConnector.fillRestaurantsWithDishes(restaurants, dishes);
        int i=0;int j=0;
        for (Restaurant r:restaurants) {
            try {
                restaurantLayout.add(newRestaurantStage(r),j,i);
                j++;
                if(j>=numberOfRows){
                    j=0;
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
            ex.printStackTrace();
        }
        centerPane.getChildren().add(restaurantLayout);
        topRestaurantsPane.getChildren().add(view);

    }

    public void goToDishes(Event event,Restaurant restaurant) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dishes.fxml"));
        Parent root=fxmlLoader.load();

        DishesController dishesController=fxmlLoader.getController();
        dishesController.iniDishes(restaurant.getDishes());
        dishesController.iniCurrentUser(currentUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root,1070,560);


        stage.setScene(scene);
        stage.show();
    }

    public VBox newRestaurantStage(Restaurant restaurant) throws IOException {
        VBox vbox =new VBox();
        HBox hbox =new HBox();
        vbox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        Label nameLabel =new Label();
        URL url = new URL(restaurant.getImageUrl());
        Image im =new Image(String.valueOf(url),350,300,true,false);
        ImageView view = new ImageView(im);

        view.resize(400,300);
        vbox.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    goToDishes(event,restaurant);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        nameLabel.setText(restaurant.getName());

        hbox.getChildren().add(nameLabel);

        vbox.getChildren().add(view);
        vbox.getChildren().add(hbox);
        return vbox;
    }
}

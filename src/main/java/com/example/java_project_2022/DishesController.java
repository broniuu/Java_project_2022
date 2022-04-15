package com.example.java_project_2022;

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
import javafx.stage.Stage;
import model.Dish;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DishesController implements Initializable {
    public ListView dishesList;
    public List<Dish> dishes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void iniDishes(List<Dish> dishes ){
        this.dishes=dishes;
        DishJdbcHelper dishJdbcHelper=new DishJdbcHelper();
        for (Dish d:dishes) {
            dishesList.getItems().add(newDishBox(d));
        }
    }
    public HBox newDishBox(Dish dish){
        FXMLLoader fxmlLoader=new FXMLLoader();
        HBox box =new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();
        Button addButton=new Button();
        addButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                String idRestaurant=""+dish.getDishId();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dishes.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 520, 540);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setUserData(idRestaurant);
                stage.setScene(scene);
                stage.show();

            }
        });
        addButton.setText("Add");
        nameLabel.setText(dish.getName());
        priceLabel.setText(""+dish.getPrice());
        box.getChildren().add(nameLabel);
        box.getChildren().add(priceLabel);
        box.getChildren().add(addButton);

        Scene scene = new Scene(box,520, 540);
        return box;
    }
}

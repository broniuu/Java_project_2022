package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import com.example.java_project_2022.databaseConnection.CartItemJdbcHelper;
import com.example.java_project_2022.model.CartItem;
import com.example.java_project_2022.model.Dish;
import com.example.java_project_2022.model.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;


/**
 * klasa Dishes controller implements initializable
 */
public class DishesController implements Initializable {
    public ListView<HBox> dishesList;
    public List<Dish> dishes;
    public Pane topDishesPane;
    User currentUser;
    MenuController controller;

    /**
     *
     * Ini current user
     *inicjalizuje obecnego uzytkownika w tej klasie
     * @param currentUser  the current user
     */
    public void iniCurrentUser(User currentUser){

        this.currentUser=currentUser;
        controller.iniCurrentUser(currentUser);
    }
    @Override


/**
 *
 * Initialize
 *  umozliwia dodanie menu przy starcie aplikacji
 * @param url  the url
 * @param resourceBundle  the resource bundle
 */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new MenuController());
        Node view = null;
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
        }
        topDishesPane.getChildren().add(view);
    }

    /**
     *
     * Ini dishes
     *inicjalizuje liste potraw
     * @param dishes  the dishes
     * @throws   MalformedURLException
     */
    public void iniDishes(List<Dish> dishes ) throws MalformedURLException {

        this.dishes=dishes;
        for (Dish d:dishes) {
            dishesList.getItems().add(newDishBox(d));
        }
    }

    /**
     *
     * New dish box
     *tworzy okno dla pojedynczej potrawy wraz z potrzebnym listenerami.
     * pojedyncze okno sklada sie z nazwy, ceny, ilosci, przyciskow + - oraz dodaj
     * @param dish  the dish
     * @return HBox
     */
    public HBox newDishBox(Dish dish) {

        final int[] Quantity = {0};
        HBox box =new HBox();
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();
        Label quntityLabel =new Label("0");
        Button plus=new Button("+");
        plus.setOnAction(event -> {
            Quantity[0]++;
            quntityLabel.setText(""+(Quantity[0]));
        });
        Button minus=new Button("-");
        minus.setOnAction(event -> {
            if(Quantity[0] >0){
                Quantity[0]--;
            }
            quntityLabel.setText(""+(Quantity[0]));
        });
        Button addButton=new Button();
        addButton.setOnAction(event -> {
            if(Quantity[0] >0){
                CartItem cartItem=new CartItem(currentUser.getUserId(),dish.getDishId(), Quantity[0]);
                System.out.println(dish.getDishId());
                CartItemJdbcHelper CIH=new CartItemJdbcHelper();
                CIH.upsertCartItem(cartItem);
                Quantity[0]=0;
                quntityLabel.setText(""+(Quantity[0]));
            }
        });

        box.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    try {
                        viewDish(dish);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        return box;
    }

    /**
     *
     * View dish
     * umozliwia podglad pojedynczego dania
     * @param dish  danie ktore chcemy zobaczyc
     * @throws   IOException
     */
    public void viewDish(Dish dish) throws IOException {

        Stage popupWindow =new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dish.fxml"));
        Scene scene = new Scene(fxmlLoader.load() ,800, 400);
        DishController dishController =fxmlLoader.getController();
        dishController.iniDish(dish);
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Dish");
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }
}

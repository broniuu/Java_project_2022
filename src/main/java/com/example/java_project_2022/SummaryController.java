package com.example.java_project_2022;

import com.google.zxing.WriterException;
import databaseConnection.CardItemJdbcHelper;
import databaseConnection.DishJdbcHelper;
import javafx.event.ActionEvent;
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
import model.CartItem;
import model.Dish;
import model.User;
import service.CartItemService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ReceiptPrinter.PdfPrinter.makePdf;
import static service.MailService.sendEmail;
import static windowCreators.SnackBarCreator.showSnackBar;
import static windowCreators.SummaryWindowCreator.thanksNote;

public class SummaryController implements Initializable {
    public Pane topSummaryPane;
    public ListView<HBox> SummaryList;
    public Label priceLabel;
    public BorderPane BPane;
    public ChoiceBox<String> deliveryBox;
    public HBox bottomPane;
    CardItemJdbcHelper cardItemJdbcHelper;
    User currentUser;
    String delivery="Delivery";
    String takeaway="Takeaway";
    List<CartItem> cartItems;
    String note="";
    String thankYouMessage="Thank you For Buying with Szama(n)!!";

    MenuController menuController;
    private double deliveryFee;
    private final double costOFDelivery=9.99;

    public void iniCurrentUser(User currentUser){
        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }
    public  void clear(){
        SummaryList.getItems().clear();
        cartItems.clear();
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

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        fxmlLoader.setControllerFactory(param -> menuController = new MenuController());
        Node view = null;
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        topSummaryPane.getChildren().add(view);
        deliveryBox.getItems().add(delivery);
        deliveryBox.getItems().add(takeaway);

        
    }
    public List<CartItem> getItems(){
        cardItemJdbcHelper = new CardItemJdbcHelper();
        return cardItemJdbcHelper.getCartItems(currentUser.getUserId());

    }
    public HBox newItemBox(CartItem cartItem){
        cardItemJdbcHelper=new CardItemJdbcHelper();
        final int[] I = {cartItem.getCountOfDish()};
        HBox box =new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();

        Label quntity=new Label(""+I[0]);
        Button plus=new Button("+");
        plus.setOnAction(event -> {
            I[0]++;
            cartItem.setCountOfDish(I[0]);
            cardItemJdbcHelper.updateCartItem(cartItem);
            quntity.setText(""+(I[0]));
            priceLabel.setText(""+(cartItem.getDish().getPrice()*I[0])+"zl");
            updatedPrice();
        });
        Button minus=new Button("-");
        minus.setOnAction(event -> {
            if(I[0] >0){
                I[0]--;
            }
            cartItem.setCountOfDish(I[0]);
            cardItemJdbcHelper.updateCartItem(cartItem);
            quntity.setText(""+(I[0]));
            priceLabel.setText(""+(cartItem.getDish().getPrice()*I[0])+"zl");
            updatedPrice();
        });
        Button delButton =new Button("delete");
        delButton.setOnAction(event -> {
            cardItemJdbcHelper.deleteCartItem( cartItem);

            iniSummary();
        });
        nameLabel.setText(cartItem.getDish().getName());
        priceLabel.setText(""+(cartItem.getDish().getPrice()*I[0]));
        box.getChildren().add(nameLabel);
        box.getChildren().add(priceLabel);
        box.getChildren().add(minus);
        box.getChildren().add(quntity);
        box.getChildren().add(plus);
        box.getChildren().add(delButton);
        return box;
    }
    public void updatedPrice() {
        if(deliveryBox.getValue()==null) deliveryBox.setValue(delivery);
        if(deliveryBox.getValue().equals(takeaway) || deliveryBox.getValue().equals(delivery)){
            double sum=0;
            for (CartItem item:cartItems) {
                sum+=(item.getDish().getPrice()*item.getCountOfDish());
            }
            priceLabel.setText((sum+deliveryFee)+"zl");
        }

    }
    public void Order(ActionEvent event) throws IOException, WriterException {
        //dodać drukowanie paragonu
        if(deliveryBox.getValue() == null){showSnackBar(bottomPane,"Choose your delivery method"); return;}
        if(cartItems.isEmpty()) {showSnackBar(bottomPane,"your Cart is empty."); return;}
        //oprożnić koszyk
        cardItemJdbcHelper=new CardItemJdbcHelper();

        for (CartItem item: cartItems) {
            cardItemJdbcHelper.deleteCartItem(item);
        }
        makePdf(currentUser,cartItems,deliveryBox.getValue().equals(delivery),note);
        cartItems.clear();
        clear();
        iniSummary();
        //wyswietlic podziekowanie za zakup
        thanksNote();
        sendEmail(currentUser.getEmail(),"Receipt for "+currentUser.getLogin(),thankYouMessage);
    }


    public void note(ActionEvent event) {
        Stage popupwindow=new Stage();
        VBox layout= new VBox(10);
        TextArea textField=new TextArea();
        if(!note.isEmpty()){
            textField.setText(note);
        }
        Button addNote=new Button("Add note");
        addNote.setOnAction(event1 -> {
            note=textField.getText();
            popupwindow.close();
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
        if(deliveryBox.getValue().equals(delivery)){
            deliveryFee=costOFDelivery;
        }
        else{
            deliveryFee=0;
        }
        updatedPrice();
    }
}

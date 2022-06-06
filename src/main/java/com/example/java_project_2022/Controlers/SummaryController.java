package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import com.example.java_project_2022.databaseConnection.CartItemJdbcHelper;
import com.example.java_project_2022.databaseConnection.DishJdbcHelper;
import com.example.java_project_2022.model.CartItem;
import com.example.java_project_2022.model.Dish;
import com.example.java_project_2022.model.User;
import com.example.java_project_2022.service.CartItemService;
import com.google.zxing.WriterException;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.java_project_2022.ReceiptPrinter.PdfPrinter.makePdf;
import static com.example.java_project_2022.service.MailService.sendEmail;
import static com.example.java_project_2022.windowCreators.SnackBarCreator.showSnackBar;


/**
 * The class Summary controller implements initializable.
 *
 * klasa obslugujaca koszyk uzytkownika z mozliwoscia zmiany ilosci produktu,
 * kupna produktu, podgladu ceny.
 */
public class SummaryController implements Initializable {
    String delivery="Delivery";
    String takeaway="Takeaway";
    String emailNotification="We send Confirmation of purchase to your email";
    String thankYouMessage="Thank you For Buying with Szama(n)!!";
    String deliveryMethod="Choose your delivery method";
    String emptyCart="Your cart is empty.";
    String noteForChief="Add note for chief / Delivery";
    String addNote="Add note";

    public Pane topSummaryPane;
    public ListView<HBox> SummaryList;
    public Label priceLabel;
    public BorderPane BPane;
    public ChoiceBox<String> deliveryBox;
    public HBox bottomPane;
    CartItemJdbcHelper cardItemJdbcHelper;
    User currentUser;

    List<CartItem> cartItems;
    String note="";


    MenuController menuController;
    private double deliveryFee;
    private final double costOFDelivery=9.99;


    /**
     *
     * inicjalizuje obecnego uzytkownika dla klasy
     *
     * @param currentUser  the current user
     */
    public void iniCurrentUser(User currentUser){

        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }

    /**
     *
     * czysci liste wyswietlana dla uzytkownika
     *
     */
    public  void clear(){

        SummaryList.getItems().clear();
        cartItems.clear();
    }

    /**
     *
     * inicjalizuje przedmioty w koszyku
     *
     */
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

/**
 *
 * Initialize
 * dodaje menu do obecnego widoku w jego gornej czesci
 *
 * @param url  the url
 * @param resourceBundle  the resource bundle
 */
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

    /**
     *
     * pobiera przedmioty z kokszyka
     *
     * @return the items
     */
    public List<CartItem> getItems(){

        cardItemJdbcHelper = new CartItemJdbcHelper();
        return cardItemJdbcHelper.getCartItems(currentUser.getUserId());

    }

    /**
     *
     * tworzy widok dla jednego przedmiotu z koszyka, widoki sa pozniej przypisywane do listy
     *
     * @param cartItem  pojedynczy przedmiot z koszyka
     * @return HBox
     */
    public HBox newItemBox(CartItem cartItem){

        cardItemJdbcHelper=new CartItemJdbcHelper();
        final int[] I = {cartItem.getCountOfDish()};
        HBox box =new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        Label nameLabel =new Label();
        Label priceLabel =new Label();
        nameLabel.setMinSize(220,20);
        nameLabel.setMaxSize(220,20);
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
                if(I[0]==0){
                    cardItemJdbcHelper.deleteCartItem( cartItem);
                    clear();
                    iniSummary();
                }
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
            clear();
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

    /**
     *
     * uaktulania cene
     *
     */
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

    /**
     *
     * jezeli kupuje rzeczy w koszyku, wysyla paragon na email
     *
     * @param event  the event
     * @throws   IOException
     * @throws  WriterException
     */
    public void Order(ActionEvent event) throws IOException, WriterException {

        //dodać drukowanie paragonu
        if(deliveryBox.getValue() == null){showSnackBar(bottomPane,deliveryMethod); return;}
        if(cartItems.isEmpty()) {showSnackBar(bottomPane,emptyCart); return;}
        //oprożnić koszyk
        cardItemJdbcHelper=new CartItemJdbcHelper();

        for (CartItem item: cartItems) {
            cardItemJdbcHelper.deleteCartItem(item);
        }
        makePdf(currentUser,cartItems,deliveryBox.getValue().equals(delivery),note);
        cartItems.clear();
        clear();
        iniSummary();
        updatedPrice();
        //wyswietlic podziekowanie za zakup
        thanksNote();
        sendEmail(currentUser.getEmail(),"Receipt for "+currentUser.getLogin(),thankYouMessage);
    }



    /**
     *
     * tworzy popup w ktorym mozna dodac notatke dla zakupu
     *
     * @param event  the event
     */
    public void note(ActionEvent event) {

        Stage popupwindow=new Stage();
        VBox layout= new VBox(10);
        TextArea textField=new TextArea();
        if(!note.isEmpty()){
            textField.setText(note);
        }
        Button addNoteButton =new Button(addNote);
        addNoteButton.setOnAction(event1 -> {
            note=textField.getText();
            popupwindow.close();
        });
        layout.getChildren().addAll(textField, addNoteButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStylesheets().add(getClass().getResource("/pop_up_style.css").toExternalForm());
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle(noteForChief);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }


    /**
     *
     * przy wybraniu ocpij dostawy dolicza 9.99 do ceny
     *
     * @param event  the event
     */
    public void Delivery(ActionEvent event) {

        if(deliveryBox.getValue().equals(delivery)){
            deliveryFee=costOFDelivery;
        }
        else{
            deliveryFee=0;
        }
        updatedPrice();
    }

    public void thanksNote() {

        Stage popupwindow=new Stage();
        VBox layout= new VBox(10);
        Label textField=new Label(thankYouMessage+"\n"+emailNotification);

        Button closeWindow =new Button("Close Window");
        closeWindow.setOnAction(event -> popupwindow.close());
        layout.getChildren().addAll(textField, closeWindow);
        layout.setAlignment(Pos.CENTER);
        layout.getStylesheets().add(getClass().getResource("/pop_up_style.css").toExternalForm());
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
}

package com.example.java_project_2022.Controlers;

import com.example.java_project_2022.HelloApplication;
import com.example.java_project_2022.databaseConnection.DishJdbcHelper;
import com.example.java_project_2022.databaseConnection.RestaurantJdbcHelper;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.java_project_2022.model.Dish;
import com.example.java_project_2022.model.Restaurant;
import com.example.java_project_2022.model.User;
import com.example.java_project_2022.service.RestaurantDishConnector;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.java_project_2022.Controlers.GlobalStatics.HEIGHT;
import static com.example.java_project_2022.Controlers.GlobalStatics.WIDTH;

/**
 * Klasa ta odpowiada za wyswietlanie list dostepnych restauracji oraz umożliwia przejscie do menu danej restauracji.
 *
 */

public class RestaurantsController implements Initializable {
    /**
     * numberOfRows sluzy do ustalenia w jakiej ilości kolumn bedą wyświtlać się restauracje.
     */
    int numberOfCols =3;
    public Pane topRestaurantsPane;
    public BorderPane BorderPaneRestaurants;
    public AnchorPane centerPane;
    /**
     * current User przechowuje obecnego Użytkownika
     */
    User currentUser;
    /**
     * menuController jest obiektem kontrollera dla menu dzieki któremu możemy dodać menu do tego widoku.
     */
    MenuController menuController;
    public void iniCurrentUser(User currentUser){
        this.currentUser=currentUser;
        menuController.iniCurrentUser(currentUser);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * w funkcji intialize wypełniamy restauracje, potrwami a następnie, tworzymy nowe widoki dla poszczegolnych restauracji
         * używając funkcji newRestaurantStage(Restaurant restaurant), po czym są one dodawane do GridPane - restaurantLayout
         * */
        RestaurantJdbcHelper restaurantsJdbcHelper = new RestaurantJdbcHelper();
        DishJdbcHelper dishJdbcHelper = new DishJdbcHelper();
        List<Dish> dishes = dishJdbcHelper.getDishes();
        List<Restaurant> restaurants = restaurantsJdbcHelper.getRestaurants();
        GridPane restaurantLayout=new GridPane();
        restaurantLayout.setGridLinesVisible(true);
        RestaurantDishConnector.fillRestaurantsWithDishes(restaurants, dishes);
        int i=0;int j=0;
        for (Restaurant r:restaurants) {
            try {
                restaurantLayout.add(newRestaurantStage(r),j,i);
                j++;
                if(j>=numberOfCols){
                    j=0;
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * nastepnie w funkcji Initilize następuje dodanie menu
         * */
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        fxmlLoader.setControllerFactory(param -> menuController = new MenuController());
        Node menu = null;
        try {
            menu = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /**
         * po czym dodajemy style do restaurantLayout a na koniec oba dodajemy do Pane'ów w borderPane restaurant layout w
         * centralnej czesci a menu do górnej
         * */
        restaurantLayout.setStyle("  -fx-padding: 10;\n" +
                "    -fx-hgap: 10;\n" +
                "    -fx-vgap: 10;" +
                " -fx-background-image: url(\"/pattern1.jpg\");");
        centerPane.getChildren().add(restaurantLayout);
        topRestaurantsPane.getChildren().add(menu);

    }
    /**
     * funkcja ta odpowiada za przejscie do listy potraw.
     * @param event akcja z przycisku
     * @param restaurant restauracja której dania chcemy przekazać do controller dishController
     *
     * zostaje utworzony tu obiekt DishesController dzieki któremu możemy wywołać funkcje iniDishes(),
     * co umożliwia przekazanie dań dla danej restauracji.
     * dzieki DishesController  możemy wywołać także funkcje iniCurrentUser(),
     * odpowiadająca za przekazanie danych obecnego użytkownika dalej.
     * */
    public void goToDishes(Event event,Restaurant restaurant) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dishes.fxml"));
        Parent root=fxmlLoader.load();
        DishesController dishesController=fxmlLoader.getController();

        dishesController.iniDishes(restaurant.getDishes());

        dishesController.iniCurrentUser(currentUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root,WIDTH,HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * W tej funkcji tworzone są view dla wyświetlenia restauracji.
     * @param restaurant -restauracja dla której tworzymy widok.
     *
     * ustawiany kontroller dla vbox wykrywa gdy zostanie on wcisniety wówczas wywołana zostaje funkcja
     * gotoDishes(Restaurant) przekazana zostaje mu restauracja.
     * */
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


        vbox.setOnMouseClicked((EventHandler<Event>) event -> {
            try {
                goToDishes(event,restaurant);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        nameLabel.setText(restaurant.getName());

        hbox.getChildren().add(nameLabel);

        vbox.getChildren().add(view);
        vbox.getChildren().add(hbox);
        vbox.setStyle(" -fx-background-color: white");
        return vbox;
    }
}

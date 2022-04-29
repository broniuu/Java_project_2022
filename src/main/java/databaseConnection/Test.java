package databaseConnection;

import model.*;
import service.RestaurantDishConnector;
import service.UserCartService;

import java.util.List;

public class Test {
    public static void main(String[] args) {

//        DishJdbcHelper dishJdbcHelper = new DishJdbcHelper();
//        List<Dish> dishes = dishJdbcHelper.getDishes();
//        RestaurantJdbcHelper restaurantJdbcHelper = new RestaurantJdbcHelper();
//        List<Restaurant> restaurants = restaurantJdbcHelper.getDRestaurants();
//        RestaurantDishConnector.fillRestaurantsWithDishes(restaurants,dishes);

        UserJdbcHelper userJdbcHelper = new UserJdbcHelper();
        User user = new User("login124", "hasło123", "Filip",
                "Broniek", "Różana 20", "1234567890",
                "12/25", "111","hehe@himaj.com");
        User user1 = new User(1,"login123", "hasło123", "Filip",
                "Broniek", "Różana 20", "1234567890",
                "12/25", "111","hehe@himaj.com");
        //boolean delSuccess = userJdbcHelper.deleteUser(user1);
        //boolean AddSuccess = userJdbcHelper.addUser(user);
        List<User> users = userJdbcHelper.getUsers();

        CardItemJdbcHelper cardItemJdbcHelper = new CardItemJdbcHelper();
        CartItem cartItem =new CartItem(2,3);
        CartItem cartItem2 =new CartItem(2,1,3);
        //boolean addItemSuccess = cardItemJdbcHelper.addCartItem(cartItem);
        List<CartItem> cartItems = cardItemJdbcHelper.getCartItems();
        List<UserCart> userCarts = UserCartService.makeUserCarts(users, cartItems);

    }
}

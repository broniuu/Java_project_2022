package databaseConnection;

import model.*;

import java.util.List;

public class Test {
    public static void main(String[] args) {
//        UserJdbcHelper userJdbcHelper = new UserJdbcHelper();
//        List<User> users = userJdbcHelper.getUsers();
//        DishJdbcHelper dishJdbcHelper = new DishJdbcHelper();
//        List<Dish> dishes = dishJdbcHelper.getDishes();
//        RestaurantJdbcHelper restaurantJdbcHelper = new RestaurantJdbcHelper();
//        List<Restaurant> restaurants = restaurantJdbcHelper.getDRestaurants();
//        User user = new User("login123", "hasło123", "Filip",
//                "Broniek", "Różana 20", "1234567890",
//                "12/25", "111","hehe@himaj.com");
//        User user1 = new User(1,"login123", "hasło123", "Filip",
//                "Broniek", "Różana 20", "1234567890",
//                "12/25", "111","hehe@himaj.com");
//        boolean delSuccess = userJdbcHelper.deleteUser(user1);
//        boolean AddSuccess = userJdbcHelper.addUser(user);
//        List<User> users2 = userJdbcHelper.getUsers();

        CardItemJdbcHelper cardItemJdbcHelper = new CardItemJdbcHelper();
        CartItem cartItem =new CartItem(1,3);
        CartItem cartItem2 =new CartItem(2,1,3);
        //boolean addItemSuccess = cardItemJdbcHelper.addCartItem(cartItem);
        //boolean delItemSuccess = cardItemJdbcHelper.deleteUser(cartItem2);
        List<CartItem> cartItems = cardItemJdbcHelper.getCartItems();
    }
}

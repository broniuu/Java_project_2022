package databaseConnection;

import model.*;
import service.CartItemService;
import service.RestaurantDishConnector;
import service.UserCartService;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        DishJdbcHelper dishJdbcHelper = new DishJdbcHelper();
        List<Dish> dishes = dishJdbcHelper.getDishes();
        RestaurantJdbcHelper restaurantJdbcHelper = new RestaurantJdbcHelper();
        List<Restaurant> restaurants = restaurantJdbcHelper.getRestaurants();
        RestaurantDishConnector.fillRestaurantsWithDishes(restaurants,dishes);

        UserJdbcHelper userJdbcHelper = new UserJdbcHelper();
        User user = new User("login1245", "hasło123", "Filip",
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
        CartItem cartItem2 =new CartItem(11,1,3);
        CartItem cartItem3 =new CartItem(31,3,2,10);
        //boolean addItemSuccess = cardItemJdbcHelper.addCartItem(cartItem);
        //boolean delItemSuccess = cardItemJdbcHelper.deleteCartItem(cartItem3);
        //boolean updateItemSuccess = cardItemJdbcHelper.updateCartItem(cartItem3);
        //boolean updateItemSuccess = cardItemJdbcHelper.updateCartItem(cartItem3);
        boolean upsertItemSuccess = cardItemJdbcHelper.upsertCartItem(cartItem);
        List<CartItem> cartItems = cardItemJdbcHelper.getCartItems();
        CartItemService.connectCartItemsWithDishesAndUsers(cartItems, dishes, users);
        List<UserCart> userCarts = UserCartService.makeUserCarts(users, cartItems);
    }
}

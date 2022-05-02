package service;

import model.CartItem;
import model.User;
import model.UserCart;

import java.util.ArrayList;
import java.util.List;

public class UserCartService {

    private static UserCart makeUserCart(User user, List<CartItem> cartItems){
        List<CartItem> userCartItems = new ArrayList<CartItem>();
        for (CartItem item : cartItems) {
            if(item.getUserId() == user.getUserId()){
                userCartItems.add(item);
            }
        }
        return new UserCart(user, userCartItems);
    }

    //metoda zwraca listę koszyków użytkowników
    public static List<UserCart> makeUserCarts(List<User> users, List<CartItem> cartItems){
        List<UserCart> userCarts = new ArrayList<UserCart>();
        for (User user : users) {
            userCarts.add(makeUserCart(user, cartItems));
        }
        return userCarts;
    }
}

package com.example.java_project_2022.service;

import com.example.java_project_2022.model.CartItem;
import com.example.java_project_2022.model.User;
import com.example.java_project_2022.model.UserCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa służy do tworzenia koszyka dla użytkownika
 */

public class UserCartService {

    /**
     * Metoda tworzy koszyk dla danego użytkownika
     *
     * @param user użytkownik
     * @param cartItems produkty z koszyka
     * @return zwraca koszyk użytkownika
     */

    private static UserCart makeUserCart(User user, List<CartItem> cartItems){
        List<CartItem> userCartItems = new ArrayList<CartItem>();
        for (CartItem item : cartItems) {
            if(item.getUserId() == user.getUserId()){
                userCartItems.add(item);
            }
        }
        return new UserCart(user, userCartItems);
    }

    /**
     * Metoda tworzy koszyki dla danych użytkowników
     *
     * @param users użytkownicy
     * @param cartItems produkty z koszyka
     * @return zwraca koszyki użytkowników
     */

    public static List<UserCart> makeUserCarts(List<User> users, List<CartItem> cartItems){
        List<UserCart> userCarts = new ArrayList<UserCart>();
        for (User user : users) {
            userCarts.add(makeUserCart(user, cartItems));
        }
        return userCarts;
    }
}

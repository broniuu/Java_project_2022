package com.example.java_project_2022.service;

import com.example.java_project_2022.model.*;

import java.util.List;

/**
 * Klasa służy do łączenia obiektów klasy CartItem z obiektami Dish i User
 */

public class CartItemService {

    /**
     * Metoda łączy prodykty z koszyka z daniami i użytkownikami
     *
     * @param cartItems produkty z koszyka
     * @param dishes dania
     * @param users użytkownicy
     */

    public static void connectCartItemsWithDishesAndUsers(List<CartItem> cartItems, List<Dish> dishes, List<User> users){
        connectCartItemsWithDishes(cartItems,dishes );
        connectCartItemsWithUsers(cartItems, users);
    }

    /**
     * Metoda łaczy produkt z koszyka z odpowiednim daniem
     *
     * @param cartItem produkt z koszyka
     * @param dishes lista dań
     */

    private static void connectCartItemWithDish(CartItem cartItem, List<Dish> dishes) {
        for (Dish dish : dishes) {
            if (dish.getDishId() == cartItem.getDishId()) {
                cartItem.setDish(dish);
            }
        }
    }

    /**
     * Metoda łaczy produkty z koszyka z odpowiednim daniem
     *
     * @param cartItems produkty z koszyka
     * @param dishes lista dań
     */

    public static void connectCartItemsWithDishes(List<CartItem> cartItems, List<Dish> dishes) {
        for (CartItem item : cartItems) {
            connectCartItemWithDish(item, dishes);
        }
    }

    /**
     * Metoda łaczy produkt z koszyka z odpowiednim użytkownikiem
     *
     * @param cartItem produkt z koszyka
     * @param users lista użytkowników
     */

    private static void connectCartItemWithUser(CartItem cartItem, List<User> users) {
        for (User user : users) {
            if (user.getUserId() == cartItem.getUserId()) {
                cartItem.setCartOwner(user);
            }
        }
    }

    /**
     * Metoda łaczy produkty z koszyka z odpowiednim użytkownikiem
     *
     * @param cartItems produkty z koszyka
     * @param users lista użytkowników
     */

    public static void connectCartItemsWithUsers(List<CartItem> cartItems, List<User> users) {
        for (CartItem item : cartItems) {
            connectCartItemWithUser(item, users);
        }
    }
}

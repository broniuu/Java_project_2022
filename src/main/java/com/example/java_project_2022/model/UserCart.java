package com.example.java_project_2022.model;

import java.util.List;

/**
 * Klasa reprezentuje koszyk użytkownika
 */


public class UserCart {
    private User user;
    private List<CartItem> cartItems;

    public UserCart(User user, List<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
    }

    public UserCart() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}

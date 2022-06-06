package com.example.java_project_2022.comparators;

import com.example.java_project_2022.model.CartItem;

import java.util.Comparator;

/**
 * Klasa służy do porównywania ze sobą obiektów klasy CartItem
 */


public class CartItemComparator implements Comparator<CartItem> {
    @Override
    public int compare(CartItem cartItem1, CartItem cartItem2) {
        if(cartItem1.getCartItemId() != 0 && cartItem2.getCartItemId() != 0){
            return cartItem1.getCartItemId() - cartItem2.getCartItemId();
        } else {
            int userIdDifference = cartItem1.getUserId() - cartItem2.getUserId();
            if (userIdDifference == 0) {
                return cartItem1.getDishId() - cartItem2.getDishId();
            }
            return userIdDifference;
        }
    }
}

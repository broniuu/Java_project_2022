package com.example.java_project_2022.model;

/**
 * Klasa reprezentuje produkt z koszyka
 */

public class CartItem {
    private int cartItemId;
    private Integer userId;
    private Integer dishId;
    private int countOfDish;
    private User cartOwner;
    private Dish dish;

    public CartItem(Integer userId, Integer dishId) {
        this.userId = userId;
        this.dishId = dishId;
        this.countOfDish = 1;
    }
    public CartItem(Integer userId, Integer dishId,int countOfDish) {
        this.userId = userId;
        this.dishId = dishId;
        this.countOfDish =countOfDish;
    }
    public CartItem(int cartItemId, Integer userId, Integer dishId, int countOfDish) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.dishId = dishId;
        this.countOfDish = countOfDish;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public User getCartOwner() {
        return cartOwner;
    }

    public void setCartOwner(User cartOwner) {
        this.cartOwner = cartOwner;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getCountOfDish() {
        return countOfDish;
    }

    public void setCountOfDish(int countOfDish) {
        this.countOfDish = countOfDish;
    }
}

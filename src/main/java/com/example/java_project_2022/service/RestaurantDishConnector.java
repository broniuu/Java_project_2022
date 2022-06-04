package com.example.java_project_2022.service;

import com.example.java_project_2022.model.Dish;
import com.example.java_project_2022.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDishConnector {

    private static void fillRestaurantWithDishes(Restaurant restaurant, List<Dish> dishes) {
        List<Dish> restaurantDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish.getRestaurantId() == restaurant.getRestaurantId()) {
                restaurantDishes.add(dish);
            }
        }
        restaurant.setDishes(restaurantDishes);
    }

    public static void fillRestaurantsWithDishes(List<Restaurant> restaurants, List<Dish> dishes) {
        for ( Restaurant restaurant : restaurants) {
            fillRestaurantWithDishes(restaurant, dishes);
        }
    }
}
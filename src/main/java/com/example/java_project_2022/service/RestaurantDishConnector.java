package com.example.java_project_2022.service;

import com.example.java_project_2022.model.Dish;
import com.example.java_project_2022.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa służy do wypełniania restauracji daniami
 */

public class RestaurantDishConnector {

    /**
     * wypełnia restaurację odpowiednimi daniami
     *
     * @param restaurant restauracja
     * @param dishes wszystkie dania
     */

    private static void fillRestaurantWithDishes(Restaurant restaurant, List<Dish> dishes) {
        List<Dish> restaurantDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish.getRestaurantId() == restaurant.getRestaurantId()) {
                restaurantDishes.add(dish);
            }
        }
        restaurant.setDishes(restaurantDishes);
    }

    /**
     * wypełnia restauracje odpowiednimi daniami
     *
     * @param restaurants restauracje
     * @param dishes wszystkie dania
     */

    public static void fillRestaurantsWithDishes(List<Restaurant> restaurants, List<Dish> dishes) {
        for ( Restaurant restaurant : restaurants) {
            fillRestaurantWithDishes(restaurant, dishes);
        }
    }
}
package com.example.java_project_2022.databaseConnection;

import com.example.java_project_2022.model.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zawiera metody potrzebne do obsługi obiektów klasy Dish w bazie danych
 */

public class DishJdbcHelper {
    public static final String DISH_TABLE = "Dishes";
    public static final String COLUMN_DISH_ID = "DishId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_RESTAURANT_ID = "RestaurantId";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_IMAGE_URL = "ImageUrl";

    DbConnector dbConnector = new DbConnector();


    /**
     * pobiera wszystkie wiersze z tabeli Dishes w bazie danych i tworzy z listę obiektów klasy Dish
     *
     * @return zwraca listę obiektów klasy Dish
     */

    public List<Dish> getDishes(){
        List<Dish> dishes = new ArrayList<>();
        String queryString = "SELECT * FROM " + DISH_TABLE;
        try (Statement stmt = this.dbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                int dishId = rs.getInt(COLUMN_DISH_ID);
                String name = rs.getString(COLUMN_NAME);
                String description = rs.getString(COLUMN_DESCRIPTION);
                Double price = rs.getDouble(COLUMN_PRICE);
                int restaurantId = rs.getInt(COLUMN_RESTAURANT_ID);
                String imageUrl = rs.getString(COLUMN_IMAGE_URL);
                Dish dish = new Dish(dishId, name, description, price, restaurantId, imageUrl);
                dishes.add(dish);
            }
            dbConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

}

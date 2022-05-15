package databaseConnection;

import model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantJdbcHelper {
    public static final String RESTAURANT_TABLE = "Restaurants";
    public static final String COLUMN_RESTAURANT_ID = "RestaurantId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_IMAGE_URL = "ImageUrl";

    DbConnector dbConnector = new DbConnector();

    public List<Restaurant> getRestaurants(){
        List<Restaurant> restaurants = new ArrayList<>();
        String queryString = "SELECT * FROM " + RESTAURANT_TABLE;
        try (Statement stmt = this.dbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                int dishId = rs.getInt(COLUMN_RESTAURANT_ID);
                String name = rs.getString(COLUMN_NAME);
                String imageUrl = rs.getString(COLUMN_IMAGE_URL);

                Restaurant restaurant = new Restaurant(dishId, name, imageUrl);
                restaurants.add(restaurant);
            }
            dbConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}

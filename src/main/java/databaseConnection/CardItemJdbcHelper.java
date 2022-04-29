package databaseConnection;

import model.CartItem;
import model.Restaurant;
import model.User;
import model.UserComparator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardItemJdbcHelper {
    public static final String SHOPPING_CART_TABLE = "ShoppingCart";
    public static final String COLUMN_CART_ITEM_ID = "CartItemId";
    public static final String COLUMN_DISH_ID = "DishId";
    public static final String COLUMN_USER_ID = "UserId";

    DbConnector dbConnector = new DbConnector();

    public List<CartItem> getCartItems(){
        List<CartItem> cartItems = new ArrayList<>();
        String queryString = "SELECT * FROM " + SHOPPING_CART_TABLE;
        try (Statement stmt = this.dbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                int cartItemId = rs.getInt(COLUMN_CART_ITEM_ID );
                Integer userId = rs.getInt(COLUMN_USER_ID);
                Integer dishId = rs.getInt(COLUMN_DISH_ID );

                CartItem cartItem = new CartItem(cartItemId, dishId, userId);
                cartItems.add(cartItem);
            }
            dbConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public boolean addCartItem(CartItem cartItem) {

        DbConnector dbConnector = new DbConnector();
        String queryString = "INSERT INTO "+ SHOPPING_CART_TABLE + " (" +
                COLUMN_CART_ITEM_ID + ", " +
                COLUMN_DISH_ID + ", " +
                COLUMN_USER_ID + " " +
                ") VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(queryString)) {
            stmt.setInt(2, cartItem.getUserId());
            stmt.setInt(3, cartItem.getDishId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteUser (CartItem cartItem){
        DbConnector dbConnector = new DbConnector();
        String queryString = "DELETE FROM " + SHOPPING_CART_TABLE + " WHERE " + COLUMN_CART_ITEM_ID + " = ?";
        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(queryString)) {
            stmt.setInt(1, cartItem.getCartItemId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

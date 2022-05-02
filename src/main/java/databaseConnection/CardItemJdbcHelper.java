package databaseConnection;

import model.CartItem;

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
    public static final String COLUMN_COUNT_OF_DISH = "CountOfDish";

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
                int countOfDish = rs.getInt(COLUMN_COUNT_OF_DISH);

                CartItem cartItem = new CartItem(cartItemId, dishId, userId, countOfDish);
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
                COLUMN_USER_ID + ", " +
                COLUMN_COUNT_OF_DISH + " " +
                ") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(queryString)) {
            stmt.setInt(2, cartItem.getUserId());
            stmt.setInt(3, cartItem.getDishId());
            stmt.setInt(4, cartItem.getCountOfDish());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public List<CartItem> getCartItems(int usId){
        List<CartItem> cartItems = new ArrayList<>();
        String queryString = "SELECT * FROM " + SHOPPING_CART_TABLE+" where UserId == "+ usId;
        try (Statement stmt = this.dbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                int cartItemId = rs.getInt(COLUMN_CART_ITEM_ID );
                Integer userId = rs.getInt(COLUMN_USER_ID);
                Integer dishId = rs.getInt(COLUMN_DISH_ID );
                int countOfDish = rs.getInt(COLUMN_COUNT_OF_DISH);

                CartItem cartItem = new CartItem(cartItemId, userId,dishId, countOfDish);

                cartItems.add(cartItem);
            }
            dbConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    public boolean deleteCartItem(CartItem cartItem){
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
    public boolean updateCartItem(CartItem cartItem) {
        DbConnector dbConnector = new DbConnector();
        String queryString = "UPDATE ShoppingCart\n" +
                "   SET \n" +
                "       CountOfDish = ?\n" +
                " WHERE CartItemId = ?;";
        try(PreparedStatement stmt = dbConnector.getConnection().prepareStatement(queryString)) {
            stmt.setInt(1, cartItem.getCountOfDish());
            stmt.setInt(2, cartItem.getCartItemId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

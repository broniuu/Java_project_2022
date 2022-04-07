package databaseConnection;

import model.Dish;
import model.User;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        UserJdbcHelper userJdbcHelper = new UserJdbcHelper();
        List<User> users = userJdbcHelper.getUsers();
        DishJdbcHelper dishJdbcHelper = new DishJdbcHelper();
        List<Dish> dishes = dishJdbcHelper.getDishes();
        User user = new User("login123", "hasło123", "Filip",
                "Broniek", "Różana 20", "1234567890",
                "12/25", "111");
        User user1 = new User(1,"login123", "hasło123", "Filip",
                "Broniek", "Różana 20", "1234567890",
                "12/25", "111");
        boolean success = userJdbcHelper.addUser(user);
    }
}

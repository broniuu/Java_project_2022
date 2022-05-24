package databaseConnection;

import model.User;
import model.UserComparator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcHelper {
    public static final String COLUMN_USER_ID = "UserId";
    public static final String USER_TABLE = "Users";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LOGIN = "Login";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SURNAME = "Surname";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_DEBIT_CARD_NUMBER = "DebitCardNumber";
    public static final String COLUMN_EXPIRE_DATE = "ExpireDate";
    public static final String COLUMN_CVV = "Cvv";
    private static final String COLUMN_EMAIL = "Email";

    public List<User> getUsers(){
        DbConnector dbConnector = new DbConnector();
        String queryString = "SELECT * FROM " + USER_TABLE;
        List<User> users = new ArrayList<>();
        try (Statement stmt = dbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                int userId = rs.getInt(COLUMN_USER_ID);
                String login = rs.getString(COLUMN_LOGIN);
                String password = rs.getString(COLUMN_PASSWORD);
                String name = rs.getString(COLUMN_NAME);
                String surname = rs.getString(COLUMN_SURNAME);
                String address = rs.getString(COLUMN_ADDRESS);
                String debitCardNumber = rs.getString(COLUMN_DEBIT_CARD_NUMBER);
                String expireDate = rs.getString(COLUMN_EXPIRE_DATE);
                String cvv = rs.getString(COLUMN_CVV);
                String email = rs.getString(COLUMN_EMAIL);
                User user = new User(userId, login, password,
                        name, surname, address, debitCardNumber,
                        expireDate, cvv, email);
                users.add(user);
            }
            dbConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(User user) {

        UserComparator userComparator = new UserComparator();
        List<User> users = this.getUsers();
        if (users.stream().anyMatch(u -> userComparator.compare(user, u) == 0)) {
            return false;
        }

        DbConnector dbConnector = new DbConnector();
        String queryString = "INSERT INTO Users (" +
                COLUMN_LOGIN + ", " +
                COLUMN_PASSWORD + ", " +
                COLUMN_NAME + ", " +
                COLUMN_SURNAME + ", " +
                COLUMN_ADDRESS + ", " +
                COLUMN_DEBIT_CARD_NUMBER + ", " +
                COLUMN_EXPIRE_DATE + ", " +
                COLUMN_CVV + ", " +
                COLUMN_EMAIL + " " +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(queryString)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getSurname());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getDebitCardNumber());
            stmt.setString(7, user.getExpireDate());
            stmt.setString(8, user.getCvv());
            stmt.setString(9, user.getEmail());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteUser (User user){
        DbConnector dbConnector = new DbConnector();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_USER_ID + " = ?";
        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(queryString)) {
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkUser(String login,String password) throws SQLException {
        DbConnector dbConnector=new DbConnector();

        Connection connection= dbConnector.getConnection();
        ResultSet resultSet=connection.createStatement().executeQuery("SELECT Login,Password FROM Users");
        while (resultSet.next()){
            if(resultSet.getString(1).equals(login) && resultSet.getString(2).equals(password)){
                dbConnector.close();
                return true;
            }
        }
        return false;
    }
    public boolean isLoginFree(String login) throws SQLException {
        DbConnector dbConnector=new DbConnector();

        Connection connection= dbConnector.getConnection();
        ResultSet resultSet=connection.createStatement().executeQuery("SELECT Login,Password FROM Users");
        while (resultSet.next()){
            if(resultSet.getString(1).equals(login)){
                dbConnector.close();
                return false;
            }
        }
        return true;
    }


    public boolean updateUser(User user){
        DbConnector dbConnector = new DbConnector();
        String queryString = "UPDATE " + USER_TABLE + "\n" +
                "   SET \n" +
                "       "+ COLUMN_LOGIN + " = ?,\n" +
                "       "+ COLUMN_PASSWORD + " = ?,\n" +
                "       "+ COLUMN_NAME + " = ?,\n" +
                "       "+ COLUMN_SURNAME + " = ?,\n" +
                "       "+ COLUMN_ADDRESS + " = ?,\n" +
                "       "+ COLUMN_DEBIT_CARD_NUMBER + " = ?,\n" +
                "       "+ COLUMN_EXPIRE_DATE + " = ?,\n" +
                "       "+ COLUMN_CVV + " = ?,\n" +
                "       "+ COLUMN_EMAIL + " = ?\n" +
                " WHERE "+ COLUMN_USER_ID +" = ?;";
        Connection connection= dbConnector.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(queryString)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getSurname());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getDebitCardNumber());
            stmt.setString(7, user.getExpireDate());
            stmt.setString(8, user.getCvv());
            stmt.setString(9, user.getEmail());
            stmt.setInt(10, user.getUserId());
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
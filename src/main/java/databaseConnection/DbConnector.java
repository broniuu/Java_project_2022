package databaseConnection;
import java.sql.*;

public class DbConnector {
    private String currentDirectoryPath = System.getProperty("user.dir");
    private final String dataBaseName = "data.db";

    public void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:/"+this.currentDirectoryPath+"\\"+this.dataBaseName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}

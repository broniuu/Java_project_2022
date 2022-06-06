package com.example.java_project_2022.databaseConnection;
import java.sql.*;

/**
 * Klasa służy do tworzenia połączenia za bazą danych
 */

public class DbConnector {
    private String currentDirectoryPath = System.getProperty("user.dir");
    private final String dataBaseName = "data.db";
    String url = "jdbc:sqlite:/"+this.currentDirectoryPath+"\\"+this.dataBaseName;

    Connection connection;

    public DbConnector() {
        connection = makeConnection();
    }

    /**
     * @return zwraca obecne połączenie z bazą danych
     */

    public Connection getConnection(){
        return(connection);
    }

    /**
     * tworzy połączenie z bazą danych
     *
     * @return zwraca połączenie z bazą danych
     */

    public Connection makeConnection(){
        try {
            Connection connection = DriverManager.getConnection(url);
            return(connection);

        }
        catch(SQLException ex) {
            System.out.println(ex.getMessage());

            return(null);
        }
    }

    /**
     * zamyka połączenie z bazą danych
     *
     */

    public void close() {
        try {

            connection.close(); }

        catch (SQLException sqle){
            System.err.println("Blad przy zamykaniu polaczenia: " + sqle);

        } }

}

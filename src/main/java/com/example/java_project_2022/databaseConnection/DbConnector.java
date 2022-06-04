package com.example.java_project_2022.databaseConnection;
import java.sql.*;

public class DbConnector {
    private String currentDirectoryPath = System.getProperty("user.dir");
    private final String dataBaseName = "data.db";
    String url = "jdbc:sqlite:/"+this.currentDirectoryPath+"\\"+this.dataBaseName;

    Connection connection;

    public DbConnector() {
        connection = makeConnection();
    }

    public Connection getConnection(){
        return(connection);
    }

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

    public void close() {
        try {

            connection.close(); }

        catch (SQLException sqle){
            System.err.println("Blad przy zamykaniu polaczenia: " + sqle);

        } }

}

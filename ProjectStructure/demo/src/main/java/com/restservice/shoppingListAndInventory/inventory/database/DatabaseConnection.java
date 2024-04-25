package com.restservice.shoppingListAndInventory.inventory.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/shms";
    private static final String USER = "root";
    private static final String PASSWORD = "1000";
    private static Connection connection = null;
    private DatabaseConnection(){}
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
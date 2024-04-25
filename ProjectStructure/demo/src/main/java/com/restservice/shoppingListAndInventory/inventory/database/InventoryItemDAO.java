package com.restservice.shoppingListAndInventory.inventory.database;

import com.restservice.shoppingListAndInventory.inventory.InventoryException;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;

import java.sql.*;
import java.time.LocalDate;

public class InventoryItemDAO {
    public void add(String name, Quantity quantity) throws SQLException, InventoryException {
        Connection con = DatabaseConnection.getConnection();
        /*String quantityType = switch (quantity.getType()) {
            case Amount -> "Amount";
            case Volume -> "Volume";
            case Weight -> "Weight";
            default -> throw new InventoryException("Unexpected quantity type");
        };*/
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into inventoryitem (product_name, quantity, buy_date) values (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setFloat(2, quantity.getValue());
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            //pstmt.setString(4, quantityType);
            pstmt.executeUpdate();
        }
    }

    public void remove(String name) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "delete from inventoryitem where product_name = ?")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void changeQuantity(String name, float quantity) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "update inventoryitem set quantity = ? where product_name = ?")) {
            pstmt.setFloat(1, quantity);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
    }
}
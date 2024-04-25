package com.restservice.shoppingListAndInventory.inventory.database;

import com.restservice.shoppingListAndInventory.inventory.InventoryException;
import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryListDAO {
    public List<InventoryItem> load() throws SQLException{
        Connection con = DatabaseConnection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT product_name, quantity, buy_date FROM inventoryitem");
        List<InventoryItem> itemList=new ArrayList<>();
        while (resultSet.next()) {
            itemList.add(new InventoryItem(resultSet.getString(1),
                            new Quantity(resultSet.getInt(2), QuantityType.Amount),
                            LocalDate.from(resultSet.getDate(3).toLocalDate()))
            );
        }
        return itemList;
    }
}

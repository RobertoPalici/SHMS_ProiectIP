package com.restservice.shoppingListAndInventory.shopping;

import com.restservice.shoppingListAndInventory.inventory.Product;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
@Getter
@Setter
@ToString
public class ShoppingItem {
    Product item;
    float price;
    public ShoppingItem(String name, Quantity quantity, float price){
        item=new Product(name, quantity);
        this.price=price;
    }
}

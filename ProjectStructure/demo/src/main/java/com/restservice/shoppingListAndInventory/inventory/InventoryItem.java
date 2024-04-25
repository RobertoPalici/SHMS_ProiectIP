package com.restservice.shoppingListAndInventory.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class InventoryItem {
    int id;
    Product item;
    LocalDate dateOfBuying;
    public InventoryItem(String name, Quantity quantity, LocalDate buyDate){
        item=new Product(name, quantity);
        this.dateOfBuying=buyDate;
    }
}
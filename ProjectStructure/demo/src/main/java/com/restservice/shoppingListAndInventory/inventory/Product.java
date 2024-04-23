package com.restservice.shoppingListAndInventory.inventory;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {
    String name;
    LocalDate expiryDate;
    Quantity quantity;
    int averageConsumption;
    public Product(String name, Quantity quantity){
        this.name=name;
        this.quantity=quantity;
    }
    public void addQuantity(float quantity){
        if(this.quantity.getValue() + quantity<0)
            this.quantity.setValue(0);
        else
            this.quantity.setValue(this.quantity.getValue() + quantity);
    }
    public void computeAverageConsumption(){
    }
}
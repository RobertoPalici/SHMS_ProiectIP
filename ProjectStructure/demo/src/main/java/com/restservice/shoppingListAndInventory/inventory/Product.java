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
    float quantity;
    int averageConsumption;
    public Product(String name, float quantity){
        this.name=name;
        this.quantity=quantity;
    }
    public void addQuantity(float quantity){
        this.quantity=this.quantity+quantity;
    }
    public void computeAverageConsumption(){
    }
}
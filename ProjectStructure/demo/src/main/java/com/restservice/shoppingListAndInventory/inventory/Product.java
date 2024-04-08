package com.restservice.shoppingListAndInventory.inventory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Product {
    String name;
    LocalDate expiryDate;
    float quantity;
    int averageConsumption;
    public void computeAverageConsumption(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
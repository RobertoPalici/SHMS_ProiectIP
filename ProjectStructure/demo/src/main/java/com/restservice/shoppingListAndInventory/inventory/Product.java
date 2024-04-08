package com.restservice.shoppingListAndInventory.inventory;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
    String name;
    LocalDate expiryDate;
    float quantity;
    int averageConsumption;
    public void computeAverageConsumption(){
    }
}
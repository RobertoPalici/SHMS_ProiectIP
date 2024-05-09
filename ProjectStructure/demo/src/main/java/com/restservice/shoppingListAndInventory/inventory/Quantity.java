package com.restservice.shoppingListAndInventory.inventory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Quantity {
    float value;
    QuantityType type;

}

package com.restservice.shoppingListAndInventory.inventory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

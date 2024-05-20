package com.restservice.shoppingListAndInventory.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Product;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_items")
@JsonIgnoreProperties(value = {"id", "list"})
public class ShoppingItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="value", column = @Column(name="quantity_value") ),
            @AttributeOverride(name="type", column = @Column(name="quantity_type"))
    } )
    Quantity quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product item;

    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private ShoppingList list;

    @Column(name = "price")
    float price;
    public ShoppingItem(String name, Quantity quantity, float price){
        item=new Product(name);
        this.price=price;
        this.quantity=quantity;
    }
    public void addQuantity(float quantity){
        if(this.quantity.getValue() + quantity<0)
            this.quantity.setValue(0);
        else
            this.quantity.setValue(this.quantity.getValue() + quantity);
    }
}

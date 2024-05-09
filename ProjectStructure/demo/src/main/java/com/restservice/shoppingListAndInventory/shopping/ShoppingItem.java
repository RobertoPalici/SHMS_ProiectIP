package com.restservice.shoppingListAndInventory.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Product;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product item;

    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private ShoppingList list;

    @Column(name = "price")
    float price;
    public ShoppingItem(String name, Quantity quantity, float price){
        item=new Product(name, quantity);
        this.price=price;
    }
}

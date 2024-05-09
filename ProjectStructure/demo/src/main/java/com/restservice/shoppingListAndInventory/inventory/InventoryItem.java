package com.restservice.shoppingListAndInventory.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventory_items")
@JsonIgnoreProperties(value = {"id", "list"})
public class InventoryItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private InventoryList list;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product item;
    @Column(name = "buy_date")
    LocalDate dateOfBuying;
    public InventoryItem(String name, Quantity quantity){
        item=new Product(name, quantity);
        this.dateOfBuying=LocalDate.now();
    }
}
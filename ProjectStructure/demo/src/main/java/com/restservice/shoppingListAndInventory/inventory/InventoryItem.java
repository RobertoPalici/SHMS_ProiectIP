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

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="value", column = @Column(name="quantity_value") ),
            @AttributeOverride(name="type", column = @Column(name="quantity_type") )
    } )
    Quantity quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product item;
    @Column(name = "buy_date")
    LocalDate dateOfBuying;
    public InventoryItem(String name, Quantity quantity){
        item=new Product(name);
        this.quantity=quantity;
        this.dateOfBuying=LocalDate.now();
    }
    public void addQuantity(float quantity){
        if(this.quantity.getValue() + quantity<0)
            this.quantity.setValue(0);
        else
            this.quantity.setValue(this.quantity.getValue() + quantity);
    }
}
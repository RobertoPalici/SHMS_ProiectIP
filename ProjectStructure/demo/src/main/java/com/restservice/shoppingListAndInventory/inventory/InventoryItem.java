package com.restservice.shoppingListAndInventory.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.household.Household;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventory_items")
@JsonIgnoreProperties(value = {"id", "list", "averageConsumption"})
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
    @Column(name = "average_consumption")
    Integer averageConsumption;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product item;
    public InventoryItem(int id, Quantity quantity, InventoryRepository inventoryRepository) throws InventoryException {
        if(inventoryRepository.productRepository.findById(id).isEmpty())
            throw new InventoryException("Id product does not exist");
        item=inventoryRepository.productRepository.findById(id).get();
        this.quantity=quantity;
    }
    public void addQuantity(float quantity){
        if(this.quantity.getValue() + quantity<0)
            this.quantity.setValue(0);
        else
            this.quantity.setValue(this.quantity.getValue() + quantity);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "quantity=" + quantity +
                ", averageConsumption=" + averageConsumption +
                ", item=" + item +
                '}';
    }
}
package com.restservice.shoppingListAndInventory.inventory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "product")
@JsonIgnoreProperties(value = {"id", "isEatable"})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    String name;
    @Column(name = "expiry_date")
    LocalDate expiryDate;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="value", column = @Column(name="quantity_value") ),
            @AttributeOverride(name="type", column = @Column(name="quantity_type") )
    } )
    Quantity quantity;
    @Column(name = "average_consumption")
    int averageConsumption;

    @Column(name = "is_eatable")
    boolean isEatable = false;
    public Product(String name, Quantity quantity){
        this.name=name;
        this.quantity=quantity;
    }
    public void addQuantity(float quantity){
        if(this.quantity.getValue() + quantity<0)
            this.quantity.setValue(0);
        else
            this.quantity.setValue(this.quantity.getValue() + quantity);
    }
    public void computeAverageConsumption(){
    }
}
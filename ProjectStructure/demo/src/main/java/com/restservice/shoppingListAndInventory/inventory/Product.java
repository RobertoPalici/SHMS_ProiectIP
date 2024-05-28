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
@Table(name = "product_temporary")
@JsonIgnoreProperties(value = {"isEatable"})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    String name;

    @Transient
    //@Column(name = "is_eatable")
    boolean isEatable = false;
    @Column(name = "category")
    int category;
    public Product(String name){
        this.name=name;
    }
    public void computeAverageConsumption(){
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
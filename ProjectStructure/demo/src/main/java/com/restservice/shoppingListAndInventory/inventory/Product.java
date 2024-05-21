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


    @Column(name = "is_eatable")
    boolean isEatable = false;
    public Product(String name){
        this.name=name;
    }
    public void computeAverageConsumption(){
    }
}
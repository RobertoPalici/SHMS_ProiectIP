package com.restservice.shoppingListAndInventory.inventory;
/*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_temporary")
@JsonIgnoreProperties(value = {"id", "isEatable"})
public class Product_Temporary {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    String name;

    //@Transient
    //@Column(name = "is_eatable")
    //boolean isEatable = false;
    @Column(name = "category")
    int category;
    public Product_Temporary(String name){
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
}*/
package com.restservice.shoppingListAndInventory.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "item_buy_history")
public class ItemBuyHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product item;
    @Column(name = "buy_date")
    LocalDate dateOfBuying;
    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private InventoryList list;
}

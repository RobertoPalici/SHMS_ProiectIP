package com.restservice.shoppingListAndInventory.notifications;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.chores.ChoresList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "notifications")
@JsonIgnoreProperties(value = {"id", "list", "type", "amount"})
public class Notification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private NotificationsList list;

    @Column(name="message")
    private String message;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name ="amount")
    private int amount;
    public Notification(NotificationType type){
        amount=1;
        this.type=type;
        this.message=amount+this.getTypeText();
    }

    public void incrementNotification(){
        amount=amount+1;
        this.message=amount+this.getTypeText();
    }

    public String getTypeText(){
        return switch (type){
            case InventoryItemAdded -> " inventory items added.";
            case InventoryItemRemoved -> " inventory items removed.";
            case ShoppingItemAdded -> " shopping items added.";
            case ShoppingItemRemoved -> " shopping items removed.";
            case ShoppingListAdded -> " shopping lists added.";
            case ShoppingListRemoved -> " shopping lists removed.";
            case ChoreAdded -> " chores added.";
            case ChoreRemoved -> " chores removed.";
            case ShoppingItemBought -> " shopping items bought.";
        };
    }
}
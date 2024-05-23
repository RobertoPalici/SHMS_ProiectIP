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
@JsonIgnoreProperties(value = {"id", "list", "type", "amount", "typeText"})
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
        message=amount+this.getTypeText();
    }

    public String getTypeText(){
        return switch (type){
            case InventoryItemAdded -> " inventory items have been added.";
            case InventoryItemRemoved -> " inventory items have been removed.";
            case ShoppingItemAdded -> " shopping items have been added.";
            case ShoppingItemRemoved -> " shopping items have been removed.";
            case ShoppingListAdded -> " shopping lists have been added.";
            case ShoppingListRemoved -> " shopping lists have been removed.";
            case ChoreAdded -> " chores have been added.";
            case ChoreRemoved -> " chores have been removed.";
            case ShoppingItemBought -> " shopping items have been bought.";
        };
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
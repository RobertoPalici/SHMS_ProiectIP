package com.restservice.shoppingListAndInventory.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.household.Household;
import com.restservice.shoppingListAndInventory.chores.ChoreRepository;
import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "notification_list")
@JsonIgnoreProperties(value = {"id", "household"})
public class NotificationsList {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id=1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "household_id", referencedColumnName = "id")
    private Household household;

    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Notification> notificationList = new ArrayList<>();


    public NotificationsList(NotificationRepository notificationRepository) {

    }
    public void clearNotifications(NotificationRepository notificationRepository){
        while(!notificationList.isEmpty()){
            notificationRepository.notificationItemRepository.delete(notificationList.get(0));
            notificationList.remove(0);
        }
    }
    public void addNotification(NotificationType type, NotificationRepository notificationRepository){
        for (Notification value : notificationList)
            if (value.getType() == type) {
                value.incrementNotification();
                return;
            }
        Notification notification=new Notification(type);
        notification.setList(this);
        notificationRepository.notificationItemRepository.save(notification);
        notificationList.add(notification);
    }
}

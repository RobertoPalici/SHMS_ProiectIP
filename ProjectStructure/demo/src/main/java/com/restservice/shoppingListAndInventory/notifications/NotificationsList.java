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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    List<Notification> notificationList = new ArrayList<>();


    public NotificationsList(NotificationRepository notificationRepository) {

    }
    public void clearNotifications(NotificationRepository notificationRepository){
        while(!notificationList.isEmpty()){
            Notification notification=notificationList.remove(0);
            notification.setList(null);
            notificationRepository.notificationItemRepository.delete(notification);
        }
    }
    public void addNotification(NotificationType type, NotificationRepository notificationRepository){
        for (Notification value : notificationList)
            if (value.getType() == type) {
                value.incrementNotification();
                notificationRepository.notificationItemRepository.save(value);
                return;
            }
        Notification notification=new Notification(type);
        notification.setList(this);
        notificationRepository.notificationItemRepository.save(notification);
        notificationList.add(notification);
    }

    @Override
    public String toString() {
        return "NotificationsList{" +
                "id=" + id +
                ", notificationList=" + notificationList +
                '}';
    }
}

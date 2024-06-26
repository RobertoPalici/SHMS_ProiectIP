package com.restservice.household;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.chores.Chore;
import com.restservice.shoppingListAndInventory.chores.ChoresHistoryList;
import com.restservice.shoppingListAndInventory.chores.ChoresList;
import com.restservice.shoppingListAndInventory.inventory.InventoryException;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import com.restservice.shoppingListAndInventory.notifications.Notification;
import com.restservice.shoppingListAndInventory.notifications.NotificationsList;
import com.restservice.shoppingListAndInventory.shopping.ShoppingException;
import com.restservice.shoppingListAndInventory.shopping.ShoppingItem;
import com.restservice.shoppingListAndInventory.shopping.ShoppingLists;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "household")
@JsonIgnoreProperties(value = {"id"})
public class Household {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id = 1;
    @OneToOne(mappedBy = "household")
    public InventoryList inventoryList;
    @OneToOne(mappedBy = "household")
    public ShoppingLists shoppingLists;
    @OneToOne(mappedBy = "household")
    public ChoresList choresList;
    @OneToOne(mappedBy = "household")
    public ChoresHistoryList choresHistoryList;
    @OneToOne(mappedBy = "household")
    public NotificationsList notificationsList;


    public Household(HouseholdRepositoriesGroup repositories){
        repositories.householdRepository.save(this);

        inventoryList=new InventoryList();
        inventoryList.setHousehold(this);
        repositories.inventoryRepository.inventoryListRepository.save(inventoryList);

        shoppingLists=new ShoppingLists();
        shoppingLists.setHousehold(this);
        repositories.shoppingRepository.shoppingListsRepository.save(shoppingLists);
        shoppingLists.addList("List 1", repositories.shoppingRepository);

        choresList=new ChoresList();
        choresList.setHousehold(this);
        repositories.choreRepository.choreListRepository.save(choresList);

        choresHistoryList=new ChoresHistoryList();
        choresHistoryList.setHousehold(this);
        repositories.choreRepository.choreHistoryListRepository.save(choresHistoryList);

        notificationsList=new NotificationsList();
        notificationsList.setHousehold(this);
        repositories.notificationRepository.notificationListRepository.save(notificationsList);
    }

    public void markItemAsBought(String indexString, String idString, HouseholdRepositoriesGroup repositories) throws ShoppingException, InventoryException {
        ShoppingItem shoppingItem=shoppingLists.removeItem(indexString, idString, repositories.shoppingRepository);
        for(int i=0;i<inventoryList.getItemList().size();i++)
            if(inventoryList.getItemList().get(i).getItem().getId()==shoppingItem.getItem().getId())
            {
                Quantity quantity=new Quantity(inventoryList.getItemList().get(i).getQuantity().getValue()+shoppingItem.getQuantity().getValue(), QuantityType.Amount);
                inventoryList.changeQuantity(i, quantity, repositories.inventoryRepository);
                return;
            }
        inventoryList.addItem(shoppingItem.getItem().getId(), shoppingItem.getQuantity(), repositories.inventoryRepository);
    }

    public static Household loadHousehold(HouseholdRepositoriesGroup repositories){
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            return iter.next();
        } else {
            return new Household(repositories);
        }
    }
}

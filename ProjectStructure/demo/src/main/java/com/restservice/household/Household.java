package com.restservice.household;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.chores.ChoresHistoryList;
import com.restservice.shoppingListAndInventory.chores.ChoresList;
import com.restservice.shoppingListAndInventory.inventory.InventoryException;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import com.restservice.shoppingListAndInventory.shopping.ShoppingException;
import com.restservice.shoppingListAndInventory.shopping.ShoppingItem;
import com.restservice.shoppingListAndInventory.shopping.ShoppingLists;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    public Household(HouseholdRepositoriesGroup repositories){
        repositories.householdRepository.save(this);

        inventoryList=new InventoryList();
        inventoryList.setHousehold(this);
        repositories.inventoryRepository.inventoryListRepository.save(inventoryList);

        shoppingLists=new ShoppingLists();
        shoppingLists.setHousehold(this);
        repositories.shoppingRepository.shoppingListsRepository.save(shoppingLists);
        shoppingLists.addList(repositories.shoppingRepository);

        choresList=new ChoresList();
        choresList.setHousehold(this);
        repositories.choreRepository.choreListRepository.save(choresList);

        choresHistoryList=new ChoresHistoryList();
        choresHistoryList.setHousehold(this);
        repositories.choreRepository.choreHistoryListRepository.save(choresHistoryList);
    }

    public void markItemAsBought(String indexString, String idString, HouseholdRepositoriesGroup repositories) throws ShoppingException, InventoryException {
        ShoppingItem shoppingItem=shoppingLists.removeItem(indexString, idString, repositories.shoppingRepository);
        for(int i=0;i<inventoryList.getItemList().size();i++)
            if(inventoryList.getItemList().get(i).getItem().getName().equals(shoppingItem.getItem().getName()))
            {
                Quantity quantity=new Quantity(inventoryList.getItemList().get(i).getQuantity().getValue()+shoppingItem.getQuantity().getValue(), QuantityType.Amount);
                inventoryList.changeQuantity(i, quantity, repositories.inventoryRepository);
                return;
            }
        inventoryList.addItem(shoppingItem.getItem().getName(), shoppingItem.getQuantity(), repositories.inventoryRepository);
    }
}

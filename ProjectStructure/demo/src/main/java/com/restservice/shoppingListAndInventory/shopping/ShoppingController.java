package com.restservice.shoppingListAndInventory.shopping;

import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.shoppingListAndInventory.inventory.InventoryException;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;

import com.restservice.shoppingListAndInventory.notifications.NotificationType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    private HouseholdRepositoriesGroup repositories;
    Household household;

    public ShoppingController() {

    }

    @PostMapping("/addList")
    public ShoppingLists addList(@RequestParam(value = "name", defaultValue = "") String name) {
        household.shoppingLists.addList(name, repositories.shoppingRepository);
        household.notificationsList.addNotification(NotificationType.ShoppingListAdded, repositories.notificationRepository);
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }

    @DeleteMapping("/removeList")
    public ShoppingLists removeList(@RequestParam(value = "index", defaultValue = "-1") String indexString) {
        try {
            household.shoppingLists.removeList(indexString, repositories.shoppingRepository);
            household.notificationsList.addNotification(NotificationType.ShoppingListRemoved, repositories.notificationRepository);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }

        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }

    @PostMapping("/addItem")
    public ShoppingLists addItem(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                 @RequestParam(value = "name", defaultValue = "") String name,
                                 @RequestParam(value = "quantity", defaultValue = "1") String quantityString,
                                 @RequestParam(value = "price", defaultValue = "0") String priceString) {


        try {
            household.shoppingLists.addItem(indexString, name, quantityString, priceString, repositories.shoppingRepository);
            household.notificationsList.addNotification(NotificationType.ShoppingItemAdded, repositories.notificationRepository);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }

    @DeleteMapping("/removeItem")
    public ShoppingLists removeItem(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                    @RequestParam(value = "id", defaultValue = "-1") String idString) {
        try {
            household.shoppingLists.removeItem(indexString, idString, repositories.shoppingRepository);
            household.notificationsList.addNotification(NotificationType.ShoppingItemRemoved, repositories.notificationRepository);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }
    @PatchMapping("/changeItemDetails")
    public ShoppingLists changeItemDetails(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                           @RequestParam(value = "id", defaultValue = "-1") String idString,
                                           @RequestParam(value = "quantity", defaultValue = "0") String quantityString,
                                           @RequestParam(value = "name", defaultValue = "") String nameString) {
        try {
            household.shoppingLists.setItemDetails(indexString, idString, nameString, quantityString, repositories.shoppingRepository);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }
    @PatchMapping("/changeQuantity")
    public ShoppingLists changeQuantity(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                        @RequestParam(value = "id", defaultValue = "-1") String idString,
                                        @RequestParam(value = "quantity", defaultValue = "0") String quantityString) {

        try {
            household.shoppingLists.changeQuantity(indexString, idString, quantityString, repositories.shoppingRepository);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }
        System.out.println(household.shoppingLists);
        return household.shoppingLists;

    }
    @PostMapping("/buyItem")
    public ShoppingLists buyItem(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                    @RequestParam(value = "id", defaultValue = "-1") String idString) {
        try {
            household.markItemAsBought(indexString, idString, repositories);
            household.notificationsList.addNotification(NotificationType.ShoppingItemBought, repositories.notificationRepository);
        } catch (ShoppingException | InventoryException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }
    @PostMapping("/autocomplete")
    public ShoppingLists autocomplete(@RequestParam(value = "name", defaultValue = "") String name) {
        try {
            household.shoppingLists.autocomplete(name);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return household.shoppingLists;
    }

    @PatchMapping("/changePrice")
    public ShoppingLists changePrice(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                     @RequestParam(value = "id", defaultValue = "-1") String idString,
                                     @RequestParam(value = "price", defaultValue = "0") String priceString) {
        try {
            household.shoppingLists.changePrice(indexString, idString, priceString);
        } catch (ShoppingException e) {
            System.out.println("Error: " + e.getMessage());
            return household.shoppingLists;
        }
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }

    @GetMapping
    public ShoppingLists getShoppingLists() {
        if (household != null)
            return household.getShoppingLists();
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            household = iter.next();
        } else {
            household=new Household(repositories);
        }
        return household.getShoppingLists();
        /*
        if (shoppingLists != null)
            return shoppingLists;
        Iterator<ShoppingLists> iterLists = shoppingRepository.shoppingListsRepository.findAll().iterator();
        if (iterLists.hasNext()) {
            shoppingLists = iterLists.next();
            if(shoppingLists.getShoppingLists().isEmpty())
                shoppingLists.addList(shoppingRepository);
        }
        else
        {
            shoppingLists = new ShoppingLists();
            shoppingRepository.shoppingListsRepository.save(shoppingLists);
            shoppingLists.addList(shoppingRepository);
        }
        return shoppingLists;*/
    }
}

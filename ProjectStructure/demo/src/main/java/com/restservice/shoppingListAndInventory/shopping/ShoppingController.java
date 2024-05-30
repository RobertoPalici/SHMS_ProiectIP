package com.restservice.shoppingListAndInventory.shopping;

import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.shoppingListAndInventory.inventory.*;

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
    //Household household;
    List<Product> productList=new ArrayList<>();

    public ShoppingController() {

    }

    @PostMapping("/addList")
    public ShoppingLists addList(@RequestParam(value = "name", defaultValue = "") String name) {
        Household household=Household.loadHousehold(repositories);
        household.shoppingLists.addList(name, repositories.shoppingRepository);
        household.notificationsList.addNotification(NotificationType.ShoppingListAdded, repositories.notificationRepository);
        System.out.println(household.shoppingLists);
        return household.shoppingLists;
    }

    @DeleteMapping("/removeList")
    public ShoppingLists removeList(@RequestParam(value = "index", defaultValue = "-1") String indexString) {
        Household household=Household.loadHousehold(repositories);
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
                                 @RequestParam(value = "name", defaultValue = "") String idString,
                                 @RequestParam(value = "quantity", defaultValue = "1") String quantityString,
                                 @RequestParam(value = "price", defaultValue = "0") String priceString) {
        Household household=Household.loadHousehold(repositories);

        try {
            household.shoppingLists.addItem(indexString, idString, quantityString, priceString, repositories);
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
        Household household=Household.loadHousehold(repositories);
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
        Household household=Household.loadHousehold(repositories);
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
        Household household=Household.loadHousehold(repositories);

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
        Household household=Household.loadHousehold(repositories);
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

    @GetMapping("/search")
    public List<Product> search(@RequestParam(value="name", defaultValue = "placeholder_test")String name){
        if(productList.isEmpty())
            for (Product product : repositories.inventoryRepository.productRepository.findAll()) {
                productList.add(product);
            }
        List<Product> list=new ArrayList<>();
        for (Product product : productList) {
            if(product.getName().toLowerCase().contains(name.toLowerCase()))
                list.add(product);
            if(list.size()==10)
                return list;
        }
        return list;
    }
    @PatchMapping("/changePrice")
    public ShoppingLists changePrice(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                     @RequestParam(value = "id", defaultValue = "-1") String idString,
                                     @RequestParam(value = "price", defaultValue = "0") String priceString) {
        Household household=Household.loadHousehold(repositories);
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
        Household household=Household.loadHousehold(repositories);
        return household.getShoppingLists();
    }
}

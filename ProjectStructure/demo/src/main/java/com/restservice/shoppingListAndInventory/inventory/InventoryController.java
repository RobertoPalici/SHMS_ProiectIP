package com.restservice.shoppingListAndInventory.inventory;

import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.household.HouseholdRepository;
import com.restservice.shoppingListAndInventory.chores.ChoresList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private HouseholdRepositoriesGroup repositories;
    Household household;

    public InventoryController() {
    }

    @PostMapping("/addItem")
    public InventoryList addItem(@RequestParam(value = "name", defaultValue = "") String name,
                                 @RequestParam(value = "quantity", defaultValue = "1") String quantityString) {
        try {
            household.inventoryList.addItem(name, quantityString, repositories.inventoryRepository);
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
            return household.inventoryList;
        }
        System.out.println(household.inventoryList);
        return household.inventoryList;
    }

    @DeleteMapping("/removeItem")
    public InventoryList removeItem(@RequestParam(value = "id", defaultValue = "-1") String idString) {
        try {
            household.inventoryList.removeItem(idString, repositories.inventoryRepository);
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
            return household.inventoryList;
        }
        System.out.println(household.inventoryList);
        return household.inventoryList;
    }

    @PatchMapping("/changeQuantity")
    public InventoryList changeQuantity(@RequestParam(value = "id", defaultValue = "-1") String idString,
                                        @RequestParam(value = "quantity", defaultValue = "0") String quantityString) {
        try {
            household.inventoryList.changeQuantity(idString, quantityString, repositories.inventoryRepository);
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
            return household.inventoryList;
        }
        System.out.println(household.inventoryList);
        return household.inventoryList;
    }

    @PatchMapping("/changeItemDetails")
    public InventoryList changeItemDetails(@RequestParam(value = "id", defaultValue = "-1") String idString,
                                           @RequestParam(value = "quantity", defaultValue = "0") String quantityString,
                                           @RequestParam(value = "name", defaultValue = "") String nameString) {
        try {
            household.inventoryList.setItemDetails(idString, nameString, quantityString, repositories.inventoryRepository);
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
            return household.inventoryList;
        }
        System.out.println(household.inventoryList);
        return household.inventoryList;
    }

    @PatchMapping("/sortItems")
    public InventoryList sortItems(@RequestParam(value = "sortFilter", defaultValue = "0") String filterString) {
        try {
            household.inventoryList.sortItems(filterString, repositories.inventoryRepository);
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
            return household.inventoryList;
        }
        System.out.println(household.inventoryList);
        return household.inventoryList;
    }

    @GetMapping
    public InventoryList getInventory() {
        if (household != null)
            return household.getInventoryList();
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            household = iter.next();
        } else {
            household=new Household(repositories);
        }
        return household.getInventoryList();
    }

    Map<Product, Integer> averageConsumptionForAll;

    public void computeAverageConsumptionForAll() {

    }

    public void suggestItems() {

    }
}
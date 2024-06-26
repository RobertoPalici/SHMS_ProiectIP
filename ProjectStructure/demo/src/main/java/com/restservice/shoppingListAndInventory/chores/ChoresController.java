package com.restservice.shoppingListAndInventory.chores;

import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import com.restservice.shoppingListAndInventory.notifications.NotificationType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin
@RequestMapping("/chores")
public class ChoresController {
    @Autowired
    private HouseholdRepositoriesGroup repositories;
    //Household household;

    ChoresController() {
    }

    @PostMapping("/addChore")
    public ChoresList addChore(@RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "description", defaultValue = "") String description,
                               @RequestParam(value = "personID", defaultValue = "-1") String personIDString,
                               @RequestParam(value = "duration", defaultValue = "-1") String durationString,
                               @RequestParam(value = "addToHistory", defaultValue = "0") String addToHistoryString) {

        Household household=Household.loadHousehold(repositories);
        try {
            household.choresList.addChore(name, description, personIDString, durationString, repositories.choreRepository);
            if (addToHistoryString.equals("1")) {
                household.choresHistoryList.addChore(name, description, personIDString, durationString, repositories.choreRepository);
            }
            household.notificationsList.addNotification(NotificationType.ChoreAdded, repositories.notificationRepository);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @DeleteMapping("/removeChore")
    public ChoresList removeChore(@RequestParam(value = "id", defaultValue = "-1") String idString) {
        Household household=Household.loadHousehold(repositories);
        try {
            household.choresList.removeChore(idString, repositories.choreRepository);
            household.notificationsList.addNotification(NotificationType.ChoreRemoved, repositories.notificationRepository);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @DeleteMapping("/removeHistoryChore")
    public ChoresHistoryList removeHistoryChore(@RequestParam(value = "id", defaultValue = "-1") String idString) {
        Household household=Household.loadHousehold(repositories);
        try {
            household.choresHistoryList.removeChore(idString, repositories.choreRepository);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresHistoryList;
        }
        System.out.println(household.choresList);
        return household.choresHistoryList;
    }

    @DeleteMapping("/clearHistory")
    public ChoresHistoryList clearHistory() {
        Household household=Household.loadHousehold(repositories);
        household.choresHistoryList.clearHistory(repositories.choreRepository);
        System.out.println(household.choresHistoryList);
        return household.choresHistoryList;
    }

    @PatchMapping("/changePersonID")
    public ChoresList changePersonID(@RequestParam(value = "id", defaultValue = "-1") String idString,
                                     @RequestParam(value = "personID", defaultValue = "-1") String personIDString) {
        Household household=Household.loadHousehold(repositories);

        try {
            household.choresList.setPersonID(idString, personIDString);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @PatchMapping("/changeItemDetails")
    public ChoresList changeItemDetails(@RequestParam(value = "id", defaultValue = "-1") String idString,
                                        @RequestParam(value = "name", defaultValue = "") String nameString,
                                        @RequestParam(value = "description", defaultValue = "") String descriptionString,
                                        @RequestParam(value = "duration", defaultValue = "") String durationString,
                                        @RequestParam(value = "personId", defaultValue = "-1") String persodIdString
    ) {
        Household household=Household.loadHousehold(repositories);
        try {
            household.choresList.setItemDetails(idString, nameString, descriptionString, durationString, persodIdString, repositories.choreRepository);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @PatchMapping("/changeDescription")
    public ChoresList changeDescription(@RequestParam(value = "id", defaultValue = "-1") String idString,
                                        @RequestParam(value = "description", defaultValue = "") String descriptionString) {
        Household household=Household.loadHousehold(repositories);
        try {
            household.choresList.setDescription(idString, descriptionString);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @PatchMapping("/changeDuration")
    public ChoresList changeDuration(@RequestParam(value = "id", defaultValue = "-1") String idString,
                                     @RequestParam(value = "duration", defaultValue = "-1") String durationString) {
        Household household=Household.loadHousehold(repositories);
        try {
            household.choresList.setDuration(idString, durationString);
        } catch (ChoresException e) {
            System.out.println("Error: " + e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @GetMapping
    public ChoresList getChores() {
        Household household=Household.loadHousehold(repositories);
        return household.getChoresList();
    }

    @GetMapping("/getHistory")
    public ChoresHistoryList getHistory() {
        Household household=Household.loadHousehold(repositories);
        return household.getChoresHistoryList();
    }
}

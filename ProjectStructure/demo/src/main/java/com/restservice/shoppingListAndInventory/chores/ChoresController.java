package com.restservice.shoppingListAndInventory.chores;

import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
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
    Household household;
    ChoresController() {
    }

    @PostMapping("/addChore")
    public ChoresList addChore(@RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "description", defaultValue = "") String description,
                               @RequestParam(value="personID", defaultValue = "-1") String personIDString,
                               @RequestParam(value="duration", defaultValue = "-1") String durationString,
                               @RequestParam(value="addToHistory", defaultValue = "0") String addToHistoryString){
        try{
            household.choresList.addChore(name, description, personIDString, durationString, repositories.choreRepository);
            if(addToHistoryString.equals("1")){
                household.choresHistoryList.addChore(name, description, personIDString, durationString, repositories.choreRepository);
            }
        }
        catch (ChoresException e){
            System.out.println("Error: "+e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @DeleteMapping("/removeChore")
    public ChoresList removeChore(@RequestParam(value="id", defaultValue = "-1") String idString){
        try{
            household.choresList.removeChore(idString, repositories.choreRepository);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @DeleteMapping("/clearHistory")
    public ChoresHistoryList clearHistory(){
        household.choresHistoryList.clearHistory(repositories.choreRepository);
        System.out.println(household.choresList);
        return household.choresHistoryList;
    }

    @PatchMapping("/changePersonID")
    public ChoresList changePersonID(@RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="personID", defaultValue = "-1") String personIDString){

        try{
            household.choresList.setPersonID(idString, personIDString);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }

    @PatchMapping("/changeItemDetails")
    public ChoresList changeItemDetails(@RequestParam(value="id", defaultValue = "-1") String idString,
                                        @RequestParam(value="name", defaultValue = "") String nameString,
                                        @RequestParam(value="description", defaultValue = "") String descriptionString,
                                        @RequestParam(value="duration", defaultValue = "") String durationString,
                                        @RequestParam(value="personId", defaultValue = "-1") String persodIdString
                                        ){

        try{
            household.choresList.setItemDetails(idString, nameString, descriptionString, durationString, persodIdString, repositories.choreRepository);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }
    @PatchMapping("/changeDescription")
    public ChoresList changeDescription(@RequestParam(value="id", defaultValue = "-1") String idString,
                                        @RequestParam(value="description", defaultValue = "") String descriptionString){
        try{
            household.choresList.setDescription(idString, descriptionString);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }
    @PatchMapping("/changeDuration")
    public ChoresList changeDuration(@RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="duration", defaultValue = "-1") String durationString){
        try{
            household.choresList.setDuration(idString, durationString);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return household.choresList;
        }
        System.out.println(household.choresList);
        return household.choresList;
    }
    @GetMapping
    public ChoresList getChores(){
        if (household != null)
            return household.getChoresList();
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            household = iter.next();
        } else {
            household=new Household(repositories);
        }
        return household.getChoresList();
    }
    @GetMapping("/getHistory")
    public ChoresHistoryList getHistory(){
        if (household != null)
            return household.getChoresHistoryList();
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            household = iter.next();
        } else {
            household=new Household(repositories);
        }
        return household.getChoresHistoryList();
    }
}

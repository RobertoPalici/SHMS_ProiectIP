package com.restservice.shoppingListAndInventory.chores;

import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin
@RequestMapping("/chores")
public class ChoresController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    ChoresList choresList = new ChoresList(entityManager);
    ChoresController() {
    }

    @PostMapping("/addChore")
    public ChoresList addChore(@RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "description", defaultValue = "") String description,
                               @RequestParam(value="personID", defaultValue = "-1") String personIDString,
                               @RequestParam(value="duration", defaultValue = "-1") String durationString){
        try{
            choresList.addChore(name, description, personIDString, durationString, entityManager);
        }
        catch (ChoresException e){
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }

    @DeleteMapping("/removeChore")
    public ChoresList removeChore(@RequestParam(value="id", defaultValue = "-1") String idString){
        try{
            choresList.removeChore(idString, entityManager);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }

    @PatchMapping("/changePersonID")
    public ChoresList changePersonID(@RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="personID", defaultValue = "-1") String personIDString){

        try{
            choresList.setPersonID(idString, personIDString);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }

    @PatchMapping("/changeDescription")
    public ChoresList changeDescription(@RequestParam(value="id", defaultValue = "-1") String idString,
                                        @RequestParam(value="description", defaultValue = "") String descriptionString){
        try{
            choresList.setDescription(idString, descriptionString);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }
    @PatchMapping("/changeDuration")
    public ChoresList changeDuration(@RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="duration", defaultValue = "-1") String durationString){
        try{
            choresList.setDuration(idString, durationString);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }
    @GetMapping
    public ChoresList getChores(){
        return choresList;
    }
}

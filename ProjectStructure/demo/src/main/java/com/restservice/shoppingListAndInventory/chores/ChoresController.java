package com.restservice.shoppingListAndInventory.chores;

import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ChoreRepository choreRepository;
    ChoresList choresList;
    ChoresController() {
    }

    @GetMapping("/addChore")
    public ChoresList addChore(@RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "description", defaultValue = "") String description,
                               @RequestParam(value="personID", defaultValue = "-1") String personIDString,
                               @RequestParam(value="duration", defaultValue = "-1") String durationString){
        try{
            choresList.addChore(name, description, personIDString, durationString, choreRepository);
        }
        catch (ChoresException e){
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }

    @GetMapping("/removeChore")
    public ChoresList removeChore(@RequestParam(value="id", defaultValue = "-1") String idString){
        try{
            choresList.removeChore(idString, choreRepository);
        }
        catch (ChoresException e) {
            System.out.println("Error: "+e.getMessage());
            return choresList;
        }
        System.out.println(choresList);
        return choresList;
    }

    @GetMapping("/changePersonID")
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

    @GetMapping("/changeDescription")
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
    @GetMapping("/changeDuration")
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
    @GetMapping(path="/start")
    public ChoresList start() {
        // This returns a JSON or XML with the users
        choresList=new ChoresList(choreRepository);
        return choresList;
    }
}

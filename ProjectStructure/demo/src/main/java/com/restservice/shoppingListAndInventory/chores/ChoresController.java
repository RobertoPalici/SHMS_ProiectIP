package com.restservice.shoppingListAndInventory.chores;

import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/chores")
public class ChoresController {
    ChoresList choresList = new ChoresList();
    ChoresController() {
    }

    @GetMapping("/addChore")
    public ChoresList addChore(@RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value="personID", defaultValue = "-1") String personIDString,
                               @RequestParam(value="duration", defaultValue = "-1") String durationString){
        int personID;
        try {
            personID = parseInt(personIDString);
        } catch (NumberFormatException e) {
            personID = 0;
        }
        int duration;
        try {
            duration = parseInt(durationString);
        } catch (NumberFormatException e) {
            duration = 0;
        }
        for(int i=0;i<choresList.getChoresList().size();i++){
            if(Objects.equals(choresList.getChoresList().get(i).getName(), name)){
                System.out.println(choresList);
                return choresList;
            }
        }
        if(personID==-1 && duration==-1)
            choresList.addChore(name);
        else if(personID!=-1 && duration==-1)
            choresList.addChore(name, personID);
        else
            choresList.addChore(name, personID, duration);
        System.out.println(choresList);
        return choresList;
    }
    @GetMapping("/removeChore")
    public ChoresList removeChore(@RequestParam(value="id", defaultValue = "-1") String idString){
        int id;
        try {
            id = parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        if(id>=0&&id<choresList.getChoresList().size())
            choresList.removeChore(id);
        System.out.println(choresList);
        return choresList;
    }
    @GetMapping("/changePersonID")
    public ChoresList changePersonID(@RequestParam(value="id", defaultValue = "-1") String idString,
                                        @RequestParam(value="personID", defaultValue = "-1") String personIDString){
        int personID;
        try {
            personID = parseInt(personIDString);
        } catch (NumberFormatException e) {
            personID = 0;
        }
        int  id;
        try {
            id = parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        if(id<0||id>choresList.getChoresList().size()-1)
            return choresList;
        choresList.getChoresList().get(id).setPersonID(personID);
        System.out.println(choresList);
        return choresList;
    }
    @GetMapping("/changeDescription")
    public ChoresList changeDescription(@RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="description", defaultValue = "") String descriptionString){
        int  id;
        try {
            id = parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        if(id<0||id>choresList.getChoresList().size()-1)
            return choresList;
        choresList.getChoresList().get(id).setDescription(descriptionString);
        System.out.println(choresList);
        return choresList;
    }
    @GetMapping("/changeDuration")
    public ChoresList changeDuration(@RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="duration", defaultValue = "-1") String durationString){
        int duration;
        try {
            duration = parseInt(durationString);
        } catch (NumberFormatException e) {
            duration = 0;
        }
        int  id;
        try {
            id = parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        if(id<0||id>choresList.getChoresList().size()-1)
            return choresList;
        choresList.getChoresList().get(id).setDuration(duration);
        System.out.println(choresList);
        return choresList;
    }
}

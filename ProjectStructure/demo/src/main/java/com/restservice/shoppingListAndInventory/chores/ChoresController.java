package com.restservice.shoppingListAndInventory.chores;

import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/chores")
public class ChoresController {
    ChoresList choresList = new ChoresList();
    ChoresController() {
    }

    @GetMapping("/addChore")
    public ChoresList addChore(@RequestParam(value = "name", defaultValue = "____") String name){
        for(int i=0;i<choresList.getChoresList().size();i++){
            if(Objects.equals(choresList.getChoresList().get(i).getName(), name)){
                System.out.println(choresList);
                return choresList;
            }
        }
        choresList.addChore(name);
        System.out.println(choresList);
        return choresList;
    }
    @GetMapping("/removeChore")
    public ChoresList removeItem(@RequestParam(value="id", defaultValue = "-1") String idString){
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
    public ChoresList changeQuantity(@RequestParam(value="id", defaultValue = "-1") String idString,
                                        @RequestParam(value="personID", defaultValue = "0") String personIDString){
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
}

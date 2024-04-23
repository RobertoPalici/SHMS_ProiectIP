package com.restservice.shoppingListAndInventory.chores;

import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ChoresList {
    List<Chore> choresList;

    public ChoresList() {
        choresList=new ArrayList<>();
    }
    public Chore getChoreAt(int index) {
        return choresList.get(index);
    }
    public void addChore(Chore chore){
        choresList.add(chore);
    }
    public void addChore(String name) {
        choresList.add(new Chore(name));
    }
    public void addChore(String name, int personID) {
        choresList.add(new Chore(name, personID));
    }
    public void addChore(String name, int personID, int duration) {
        choresList.add(new Chore(name, personID, duration));
    }
    public void removeChore(int index) {
        choresList.remove(index);
    }
}

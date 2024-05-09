package com.restservice.shoppingListAndInventory.chores;

import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ChoresList {
    List<Chore> choresList;

    public ChoresList() {
        choresList=new ArrayList<>();
    }
    public int findChoreIndex(String name){
        for(int i=0; i<choresList.size(); i++) {
            if(Objects.equals(choresList.get(i).getName(), name))
                return i;
        }
        return -1;
    }
    public Chore getChoreAt(int index) {
        return choresList.get(index);
    }
    public void addChore(Chore chore){
        choresList.add(chore);
    }
    public void addChore(String name, String description, int personID, int duration) throws ChoresException {
        if(name.isEmpty())
            throw new ChoresException("Chore name cannot be empty.");
        if(personID < 0 && personID != -1)
            throw new ChoresException("Person ID cannot be negative.");
        if(duration < 0 && duration != -1)
            throw new ChoresException("Duration cannot be negative.");

        int index=findChoreIndex(name);
        if(index==-1)
            choresList.add(new Chore(name, description, personID, duration));
        else
            throw new ChoresException("Chore already exists.");
    }
    public void addChore(String name, String description, String personIDString, String durationString) throws ChoresException {
        int personID;
        int duration;
        try{
            personID=Integer.parseInt(personIDString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Person ID has to be a non-negative integer.");
        }
        try{
            duration=Integer.parseInt(durationString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Duration has to be a non-negative integer.");
        }
        this.addChore(name, description, personID, duration);
    }

    public void removeChore(String idString) throws ChoresException {
        int id;
        try{
            id=Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        }
        this.removeChore(id);
    }
    public void removeChore(int id) throws ChoresException {
        if(id<0)
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        if(id>=choresList.size())
            throw new ChoresException("Chore ID cannot be bigger than the list's size.");
        choresList.remove(id);
    }
    public void setPersonID(String idString, String personIDString) throws ChoresException {
        int id;
        try{
            id=Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        }
        int personID;
        try{
            personID=Integer.parseInt(personIDString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        }
        this.setPersonID(id, personID);
    }
    public void setPersonID(int id, int personID) throws ChoresException {
        if(id<0)
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        if(id>=choresList.size())
            throw new ChoresException("Chore ID cannot be bigger than the list's size.");
        if(personID < 0 && personID != -1)
            throw new ChoresException("Person ID has to be a non-negative integer.");
        choresList.get(id).setPersonID(personID);
    }
    public void setDescription(String idString, String descriptionString) throws ChoresException {
        int id;
        try{
            id=Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        }
        this.setDescription(id, descriptionString);
    }
    public void setDescription(int id, String description) throws ChoresException {
        if(id<0)
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        if(id>=choresList.size())
            throw new ChoresException("Chore ID cannot be bigger than the list's size.");
        choresList.get(id).setDescription(description);
    }
    public void setDuration(String idString, String durationString) throws ChoresException {
        int id;
        try{
            id=Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        }
        int duration;
        try{
            duration=Integer.parseInt(durationString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Duration has to be a non-negative integer.");
        }
        this.setDuration(id, duration);
    }
    public void setDuration(int id, int duration) throws ChoresException {
        if(id<0)
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        if(id>=choresList.size())
            throw new ChoresException("Chore ID cannot be bigger than the list's size.");
        if(duration < 0 && duration != -1)
            throw new ChoresException("Duration has to be a non-negative integer.");
        choresList.get(id).setDuration(duration);
    }
}

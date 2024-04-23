package com.restservice.shoppingListAndInventory.chores;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chore {
    String name;
    String description;
    int personID;
    public Chore(String name) {
        this.name=name;
    }
    public void addChoreDescription(String description) {
        this.description=description;
    }
    public void addPerson(int personID) {
        this.personID=personID;
    }
}

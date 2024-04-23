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
    int duration;
    public Chore(String name) {
        this.name=name;
    }
    public Chore(String name, int personID) {this.name=name; this.personID=personID; }
    public Chore(int duration, String name) {this.duration=duration; this.name=name; }
    public Chore(String name, int personID, int duration) {this.name=name; this.duration=duration; this.personID=personID; }
    public void addChoreDescription(String description) {
        this.description=description;
    }
    public void addPerson(int personID) {
        this.personID=personID;
    }
    public void addDuration(int duration) {this.duration=duration; }
}

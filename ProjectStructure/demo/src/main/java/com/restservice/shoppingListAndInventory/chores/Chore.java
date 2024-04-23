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

    public Chore(String name, int personID, int duration) {this.name=name; this.duration=duration; this.personID=personID; }
}

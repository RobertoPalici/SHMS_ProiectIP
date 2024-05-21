package com.restservice.shoppingListAndInventory.chores;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chore_history_items")
@JsonIgnoreProperties(value = {"id", "list"})
public class ChoreHistoryItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private ChoresHistoryList list;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "person_id")
    int personID;

    @Column(name = "duration")
    String duration;

    public ChoreHistoryItem(String name, String description, int personID, String duration) {this.name=name; this.description=description; this.duration=duration; this.personID=personID; }

    @Override
    public String toString() {
        return "Chore{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", personID=" + personID +
                ", duration=" + duration +
                '}';
    }
}

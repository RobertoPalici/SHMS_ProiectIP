package com.restservice.shoppingListAndInventory.chores;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chores_list")
@JsonIgnoreProperties(value = {"id"})
public class ChoresList {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id=1;

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Chore> choresList = new ArrayList<>();

    public ChoresList(ChoreRepository choreRepository) {
        for (Chore item : choreRepository.choreItemRepository.findAll()) {
            choresList.add(item);
        }
    }
    private static List<Chore> loadAllData(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Chore> criteria = builder.createQuery(Chore.class);
        criteria.from(Chore.class);
        return session.createQuery(criteria).getResultList();
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
    public void addChore(String name, String description, int personID, int duration, ChoreRepository choreRepository) throws ChoresException {
        if(name.isEmpty())
            throw new ChoresException("Chore name cannot be empty.");
        if(personID < 0 && personID != -1)
            throw new ChoresException("Person ID cannot be negative.");
        if(duration < 0 && duration != -1)
            throw new ChoresException("Duration cannot be negative.");
        int index=findChoreIndex(name);
        if(index!=-1)
            throw new ChoresException("Chore already exists.");
        Chore chore = new Chore(name, description, personID, duration);
        chore.setList(this);
        choreRepository.choreItemRepository.save(chore);
        choresList.add(chore);
    }
    public void addChore(String name, String description, String personIDString, String durationString, ChoreRepository choreRepository) throws ChoresException {
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
        this.addChore(name, description, personID, duration, choreRepository);
    }

    public void removeChore(String idString, ChoreRepository choreRepository) throws ChoresException {
        int id;
        try{
            id=Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        }
        this.removeChore(id, choreRepository);
    }
    public void removeChore(int id, ChoreRepository choreRepository) throws ChoresException {
        if(id<0)
            throw new ChoresException("Chore ID has to be a non-negative integer.");
        if(id>=choresList.size())
            throw new ChoresException("Chore ID cannot be bigger than the list's size.");
        choreRepository.choreItemRepository.delete(choresList.get(id));
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

    @Override
    public String toString() {
        return "ChoresList{" +
                "choresList=" + choresList +
                '}';
    }
}

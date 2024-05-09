package com.restservice.shoppingListAndInventory.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventory_list")
@JsonIgnoreProperties(value = {"id", "list"})
public class InventoryList {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id=1;

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<InventoryItem> itemList = new ArrayList<>();

    @Transient
    private boolean is_persisted=false;

    public InventoryList(EntityManager entityManager){
        Session session = entityManager.unwrap(Session.class);
        itemList=loadAllData(session);
        entityManager.getTransaction().begin();
        entityManager.merge(this);
        entityManager.getTransaction().commit();
    }
    private static List<InventoryItem> loadAllData(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<InventoryItem> criteria = builder.createQuery(InventoryItem.class);
        criteria.from(InventoryItem.class);
        return session.createQuery(criteria).getResultList();
    }
    public int findItemIndex(String name){
        for(int i=0;i<itemList.size();i++){
            if(Objects.equals(itemList.get(i).getItem().getName(), name)){
                //itemList.get(i).getItem().addQuantity(quantity);
                return i;
            }
        }
        return -1;
    }
    public InventoryItem getItemAt(int index){
        return itemList.get(index);
    }
    public void addItem(InventoryItem item, EntityManager entityManager){
        item.setList(this);
        itemList.add(item);
    }
    public void addItem(String name, Quantity quantity, EntityManager entityManager) throws InventoryException{
        if(name.isEmpty())
            throw new InventoryException("Item name cannot be empty.");
        if(quantity.value<0&&quantity.value!=-1)
            throw new InventoryException("Quantity cannot be negative.");
        /*int index=findItemIndex(name);
        if(index>=0)
            itemList.get(index).getItem().addQuantity(quantity.value);
        else
        {
        }*/
        InventoryItem item = new InventoryItem(name,quantity);
        item.setList(this);
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
        itemList.add(item);
    }
    public void addItem(String name, String quantityString, EntityManager entityManager) throws InventoryException{
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Quantity has to be a number.");
        }
        this.addItem(name, new Quantity(quantity, QuantityType.Amount), entityManager);
    }
    public void removeItem(String idString, EntityManager entityManager) throws InventoryException{
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        this.removeItem(id, entityManager);
    }
    public void removeItem(int id, EntityManager entityManager) throws InventoryException{
        if(id<0)
            throw new InventoryException("Item ID has to be a non-negative integer.");
        if(id>=itemList.size())
            throw new InventoryException("Item ID cannot be bigger that the list's size.");
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(itemList.get(id)) ? itemList.get(id) : entityManager.merge(itemList.get(id)));
        entityManager.getTransaction().commit();
        itemList.remove(id);
    }
    public void changeQuantity(String idString, String quantityString, EntityManager entityManager) throws InventoryException{
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Quantity has to be a number.");
        }
        int  id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        changeQuantity(id, new Quantity(quantity, QuantityType.Amount), entityManager);
    }
    public void changeQuantity(int id, Quantity quantity, EntityManager entityManager) throws InventoryException{
        if(id<0||id>itemList.size()-1)
            throw new InventoryException("Item ID has to be a non-negative integer and cannot be bigger that the list's size.");

        itemList.get(id).getItem().setQuantity(quantity);
        entityManager.getTransaction().begin();
        entityManager.merge(itemList.get(id));
        entityManager.getTransaction().commit();
    }
}

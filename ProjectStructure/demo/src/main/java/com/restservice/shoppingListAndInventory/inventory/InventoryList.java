package com.restservice.shoppingListAndInventory.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
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
@JsonIgnoreProperties(value = {"id", "itemBuyHistoryList"})
public class InventoryList{
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id=1;

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<InventoryItem> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<InventoryItem> itemBuyHistoryList = new ArrayList<>();

    @Transient
    private boolean is_persisted=false;

    public InventoryList(InventoryRepository inventoryRepository){
        for (InventoryItem item : inventoryRepository.inventoryItemRepository.findAll()) {
            itemList.add(item);
        }
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
    public void addItem(InventoryItem item, InventoryRepository inventoryRepository){
        item.setList(this);
        itemList.add(item);
    }
    public void addItem(String name, Quantity quantity, InventoryRepository inventoryRepository) throws InventoryException{
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
        inventoryRepository.inventoryItemRepository.save(item);
        itemList.add(item);
    }
    public void addItem(String name, String quantityString, InventoryRepository inventoryRepository) throws InventoryException{
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Quantity has to be a number.");
        }
        this.addItem(name, new Quantity(quantity, QuantityType.Amount), inventoryRepository);
    }
    public void removeItem(String idString, InventoryRepository inventoryRepository) throws InventoryException{
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        this.removeItem(id, inventoryRepository);
    }
    public void removeItem(int id, InventoryRepository inventoryRepository) throws InventoryException{
        if(id<0)
            throw new InventoryException("Item ID has to be a non-negative integer.");
        if(id>=itemList.size())
            throw new InventoryException("Item ID cannot be bigger that the list's size.");
        inventoryRepository.inventoryItemRepository.delete(itemList.get(id));
        itemList.remove(id);
    }
    public void changeQuantity(String idString, String quantityString, InventoryRepository inventoryRepository) throws InventoryException{
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
        changeQuantity(id, new Quantity(quantity, QuantityType.Amount), inventoryRepository);
    }
    public void changeQuantity(int id, Quantity quantity, InventoryRepository inventoryRepository) throws InventoryException{
        if(id<0||id>itemList.size()-1)
            throw new InventoryException("Item ID has to be a non-negative integer and cannot be bigger that the list's size.");

        itemList.get(id).setQuantity(quantity);
        inventoryRepository.inventoryItemRepository.save(itemList.get(id));
    }
}

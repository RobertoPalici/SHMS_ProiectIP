package com.restservice.shoppingListAndInventory.inventory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class InventoryList {
    List<InventoryItem> itemList;
    public InventoryList(){
        itemList=new ArrayList<>();
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
    public void addItem(InventoryItem item){
        itemList.add(item);
    }
    public void addItem(String name, Quantity quantity) throws InventoryException{
        if(name.isEmpty())
            throw new InventoryException("Item name cannot be empty.");
        if(quantity.value<0&&quantity.value!=-1)
            throw new InventoryException("Quantity cannot be negative.");
        int index=findItemIndex(name);
        if(index>=0)
            itemList.get(index).getItem().addQuantity(quantity.value);
        else
            itemList.add(new InventoryItem(name,quantity));
    }
    public void addItem(String name, String quantityString) throws InventoryException{
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Quantity has to be a number.");
        }
        this.addItem(name, new Quantity(quantity, QuantityType.Amount));
    }
    public void removeItem(String idString) throws InventoryException{
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        this.removeItem(id);
    }
    public void removeItem(int id) throws InventoryException{
        if(id<0)
            throw new InventoryException("Item ID has to be a non-negative integer.");
        if(id>=itemList.size())
            throw new InventoryException("Item ID cannot be bigger that the list's size.");
        itemList.remove(id);
    }
    public void changeQuantity(String idString, String quantityString) throws InventoryException{
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
        changeQuantity(id, new Quantity(quantity, QuantityType.Amount));
    }
    public void changeQuantity(int id, Quantity quantity) throws InventoryException{
        if(id<0||id>itemList.size()-1)
            throw new InventoryException("Item ID has to be a non-negative integer and cannot be bigger that the list's size.");
        itemList.get(id).getItem().setQuantity(quantity);
    }
}

package com.restservice.shoppingListAndInventory.shopping;

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
public class ShoppingList {
    List<ShoppingItem> shoppingList;
    //EfficientRoute route;
    public void computeEfficientRoute(){

    }
    public ShoppingList(){
        shoppingList=new ArrayList<>();
    }
    public ShoppingItem getItemAt(int index){
        return shoppingList.get(index);
    }
    public void addItem(ShoppingItem item){
        shoppingList.add(item);
    }
    public void addItem(String name, Quantity quantity){
        shoppingList.add(new ShoppingItem(name,quantity));
    }
    public void removeItem(int index){
        shoppingList.remove(index);
    }
}

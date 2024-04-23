package com.restservice.shoppingListAndInventory.shopping;

import com.restservice.shoppingListAndInventory.inventory.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public int findItemIndex(String name){
        for(int i=0;i<shoppingList.size();i++){
            if(Objects.equals(shoppingList.get(i).getItem().getName(), name)){
                return i;
            }
        }
        return -1;
    }
    public ShoppingItem getItemAt(int index){
        return shoppingList.get(index);
    }
    public void addItem(ShoppingItem item){
        shoppingList.add(item);
    }
    public void addItem(String name, Quantity quantity, float price) throws ShoppingException {
        if(name.isEmpty())
            throw new ShoppingException("Item name cannot be empty.");
        if(quantity.getValue()<0&&quantity.getValue()!=-1)
            throw new ShoppingException("Quantity cannot be negative.");
        if(price < 0)
            throw new ShoppingException("Price cannot be nagative.");
        int index = findItemIndex(name);
        if(index>=0)
            shoppingList.get(index).getItem().addQuantity(quantity.getValue());
        else
            shoppingList.add(new ShoppingItem(name, quantity,price));
    }
    public void addItem(String name, String quantityString, String priceString) throws ShoppingException{
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Quantity has to be a number.");
        }
        float price;
        try {
            price = Float.parseFloat(priceString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Price has to be a number.");
        }
        this.addItem(name, new Quantity(quantity, QuantityType.Amount), price);
    }
    public void removeItem(String idString) throws ShoppingException{
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        }
        this.removeItem(id);
    }
    public void removeItem(int id) throws ShoppingException{
        if(id<0)
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        if(id>=shoppingList.size())
            throw new ShoppingException("Item ID cannot be bigger that the list's size.");
        shoppingList.remove(id);
    }
    public void changeQuantity(String idString, String quantityString) throws ShoppingException{
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Quantity has to be a number.");
        }
        int  id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        }
        this.changeQuantity(id, new Quantity(quantity, QuantityType.Amount));
    }
    public void changeQuantity(int id, Quantity quantity) throws ShoppingException{
        if(id<0||id>shoppingList.size()-1)
            throw new ShoppingException("Item ID has to be a non-negative integer and cannot be bigger that the list's size.");
        shoppingList.get(id).getItem().addQuantity(quantity.getValue());
    }
    public void changePrice(String idString, String priceString) throws ShoppingException{
        float price;
        try {
            price = Float.parseFloat(priceString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Price has to be a number.");
        }
        int  id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        }
        this.changePrice(id, price);
    }
    public void changePrice(int id, float price) throws ShoppingException{
        if(id<0)
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        if(id>=shoppingList.size())
            throw new ShoppingException("Item ID cannot be bigger that the list's size.");
        if(shoppingList.get(id).getPrice() + price < 0)
            shoppingList.get(id).setPrice(0);
        else shoppingList.get(id).setPrice(price + shoppingList.get(id).getPrice());
    }


}

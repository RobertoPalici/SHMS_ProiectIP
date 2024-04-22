package com.restservice.shoppingListAndInventory.shopping;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@RestController
public class ShoppingController {
    List<ShoppingList> shoppingLists = new ArrayList<>();
    public ShoppingController(){

    }
    @RequestMapping("/addList")
    public void addList(){
        shoppingLists.add(new ShoppingList());
        System.out.println(shoppingLists);
    }
    @RequestMapping("/removeList")
    public void removeList(@RequestParam(value = "index", defaultValue = "-1") String indexString){
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            index = 0;
        }
        if(shoppingLists.isEmpty())
        {
            System.out.println("There are no lists to delete");
            return;
        }
        else if(index > shoppingLists.size()-1)
            shoppingLists.remove(shoppingLists.size()-1);

        else if(index<0)
            index=shoppingLists.size()-1;
        else
            shoppingLists.remove(index);

        System.out.println(shoppingLists);
    }

    @RequestMapping("/addShoppingItem")
    public void addItem (@RequestParam(value="index", defaultValue = "-1") String indexString,
                         @RequestParam(value = "name", defaultValue = "____") String name,
                         @RequestParam(value = "quantity", defaultValue = "1") String quantityString) {


        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            index = 0;
        }
        if(shoppingLists.isEmpty()) {
            shoppingLists.add(new ShoppingList());
            index=0;
        }else if(index > shoppingLists.size()-1)
        {
            shoppingLists.add(new ShoppingList());
            index= shoppingLists.size()-1;
        }else if (index<0)
            index= shoppingLists.size()-1;
        for(int i=0;i<shoppingLists.get(index).getShoppingList().size();i++){
            if(Objects.equals(shoppingLists.get(index).getShoppingList().get(i).getItem().getName(), name)){
                shoppingLists.get(index).getShoppingList().get(i).getItem().addQuantity(quantity);
                System.out.println(shoppingLists.get(index));
                return;
            }
        }
        shoppingLists.get(index).addItem(name,new Quantity(quantity, QuantityType.Amount));
        System.out.println(shoppingLists);
    }

    @RequestMapping("/removeShoppingItem")
    public void removeItem(@RequestParam(value="index", defaultValue = "-1") String indexString,
                           @RequestParam(value="id", defaultValue = "-1") String idString){
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            index = 0;
        }
        if(index > shoppingLists.size()-1 || index<0)
            index=shoppingLists.size()-1;

        if(id > shoppingLists.get(index).getShoppingList().size() || id <0)
            id=shoppingLists.get(index).getShoppingList().size()-1;

        shoppingLists.get(index).getShoppingList().remove(id);

        System.out.println(shoppingLists);
    }
    @RequestMapping("/changeShoppingQuantity")
    public void changeQuantity(@RequestParam(value="index", defaultValue = "-1") String indexString,
                               @RequestParam(value="id", defaultValue = "-1") String idString,
                               @RequestParam(value="quantity", defaultValue = "0") String quantityString){
        float quantity;

        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        int  id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            index = 0;
        }

        if(index > shoppingLists.size()-1 || index<0)
            index=shoppingLists.size()-1;
        if(id > shoppingLists.get(index).getShoppingList().size() || id <0)
            id=shoppingLists.get(index).getShoppingList().size()-1;
        float currentQuantity = shoppingLists.get(index).getShoppingList().get(id).getItem().getQuantity().getValue();
        if(currentQuantity + quantity > 0)
            shoppingLists.get(index).getShoppingList().get(id).getItem().getQuantity().setValue(currentQuantity + quantity);
        else shoppingLists.get(index).getShoppingList().get(id).getItem().getQuantity().setValue(0);
        System.out.println(shoppingLists);

    }
    @RequestMapping("/autocomplete")
    public void autocomplete(@RequestParam(value="name", defaultValue = "") String name){
        String newName;
        if(Character.isLowerCase(name.charAt(0)))
        {
            char firstChar = Character.toUpperCase(name.charAt(0));
            newName = firstChar + name.substring(1);
        }
        else newName = name;

        for(int i=0; i<shoppingLists.size();i++)
            for(int j=0; j<shoppingLists.get(i).getShoppingList().size(); j++)
            {
                if(shoppingLists.get(i).getShoppingList().get(j).getItem().getName().startsWith(newName))
                System.out.println("Did you mean " + shoppingLists.get(i).getShoppingList().get(j).getItem().getName() + " ?");
            }
    }

}

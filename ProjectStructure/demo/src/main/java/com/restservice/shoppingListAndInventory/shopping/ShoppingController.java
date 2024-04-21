package com.restservice.shoppingListAndInventory.shopping;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;

import org.springframework.web.bind.annotation.*;

import java.util.Objects;
@RestController
public class ShoppingController {
    ShoppingList shoppingList =  new ShoppingList();
    public ShoppingController(){

    }
    @RequestMapping("/addShoppingItem")
    public void addItem(@RequestParam(value = "name", defaultValue = "____") String name,
                        @RequestParam(value = "quantity", defaultValue = "1") String quantityString){
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        for(int i=0;i<shoppingList.getShoppingList().size();i++){
            if(Objects.equals(shoppingList.getShoppingList().get(i).getItem().getName(), name)){
                shoppingList.getShoppingList().get(i).getItem().addQuantity(quantity);
                System.out.println("Shopping List: " + shoppingList);
                return;
            }
        }
        shoppingList.addItem(name,new Quantity(quantity, QuantityType.Amount));
        System.out.println(shoppingList);
    }

    @RequestMapping("/removeShoppingItem")
    public void removeItem(@RequestParam(value="id", defaultValue = "-1") String idString){
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        if(id>=0&&id<shoppingList.getShoppingList().size())
            shoppingList.removeItem(id);
        System.out.println(shoppingList);
    }
    @RequestMapping("/changeShoppingQuantity")
    public void changeQuantity(@RequestParam(value="id", defaultValue = "-1") String idString,
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
        float currentQuantity = shoppingList.getShoppingList().get(id).getItem().getQuantity().getValue();
        if(currentQuantity + quantity > 0)
            shoppingList.getShoppingList().get(id).getItem().getQuantity().setValue(currentQuantity + quantity);
        else shoppingList.getShoppingList().get(id).getItem().getQuantity().setValue(0);
        System.out.println(shoppingList);

    }

}

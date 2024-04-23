package com.restservice.shoppingListAndInventory.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    InventoryList itemList=new InventoryList();
    public InventoryController(){
    }

    @GetMapping("/addItem")
    public InventoryList addItem(@RequestParam(value = "name", defaultValue = "") String name,
                        @RequestParam(value = "quantity", defaultValue = "1") String quantityString){
        try{
            itemList.addItem(name,quantityString);
        }
        catch (InventoryException e){
            System.out.println("Error: "+e.getMessage());
            return itemList;
        }
        System.out.println(itemList);
        return itemList;
    }
    @GetMapping("/removeItem")
    public InventoryList removeItem(@RequestParam(value="id", defaultValue = "-1") String idString){
        try{
            itemList.removeItem(idString);
        }
        catch (InventoryException e){
            System.out.println("Error: "+e.getMessage());
            return itemList;
        }
        System.out.println(itemList);
        return itemList;
    }
    @GetMapping("/changeQuantity")
    public InventoryList changeQuantity(@RequestParam(value="id", defaultValue = "-1") String idString,
                               @RequestParam(value="quantity", defaultValue = "0") String quantityString){
        try{
            itemList.changeQuantity(idString,quantityString);
        }
        catch (InventoryException e)
        {
            System.out.println("Error: "+e.getMessage());
            return itemList;
        }
        System.out.println(itemList);
        return itemList;
    }
    Map<Product, Integer> averageConsumptionForAll;
    public void computeAverageConsumptionForAll(){

    }
    public void suggestItems(){

    }
}
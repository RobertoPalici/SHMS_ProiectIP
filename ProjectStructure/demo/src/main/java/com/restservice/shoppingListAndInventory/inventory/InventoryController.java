package com.restservice.shoppingListAndInventory.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class InventoryController {

    InventoryList itemList=new InventoryList();
    public InventoryController(){
    }

    @RequestMapping("/addItem")
    public void addItem(@RequestParam(value = "name", defaultValue = "____") String name,
                        @RequestParam(value = "quantity", defaultValue = "1") String quantityString){
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        for(int i=0;i<itemList.getItemList().size();i++){
            if(Objects.equals(itemList.getItemList().get(i).getItem().getName(), name)){
                itemList.getItemList().get(i).getItem().addQuantity(quantity);
                System.out.println(itemList);
                return;
            }
        }
        itemList.addItem(name,quantity);
        System.out.println(itemList);
    }
    @RequestMapping("/removeItem")
    public void removeItem(@RequestParam(value="id", defaultValue = "-1") String idString){
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        if(id>=0&&id<itemList.getItemList().size())
            itemList.removeItem(id);
        System.out.println(itemList);
    }
    Map<Product, Integer> averageConsumptionForAll;
    public void computeAverageConsumptionForAll(){

    }
    public void suggestItems(){

    }
}
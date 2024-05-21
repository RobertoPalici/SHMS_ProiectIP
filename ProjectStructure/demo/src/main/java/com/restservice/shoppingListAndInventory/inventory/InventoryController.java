package com.restservice.shoppingListAndInventory.inventory;

import com.restservice.shoppingListAndInventory.chores.ChoresList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;
    InventoryList itemList;
    public InventoryController(){
    }

    @PostMapping("/addItem")
    public InventoryList addItem(@RequestParam(value = "name", defaultValue = "") String name,
                        @RequestParam(value = "quantity", defaultValue = "1") String quantityString){
        try{
            itemList.addItem(name,quantityString,inventoryRepository);
        }
        catch (InventoryException e){
            System.out.println("Error: "+e.getMessage());
            return itemList;
        }
        System.out.println(itemList);
        return itemList;
    }
    @DeleteMapping("/removeItem")
    public InventoryList removeItem(@RequestParam(value="id", defaultValue = "-1") String idString){
        try{
            itemList.removeItem(idString,inventoryRepository);
        }
        catch (InventoryException e){
            System.out.println("Error: "+e.getMessage());
            return itemList;
        }
        System.out.println(itemList);
        return itemList;
    }
    @PatchMapping("/changeQuantity")
    public InventoryList changeQuantity(@RequestParam(value="id", defaultValue = "-1") String idString,
                               @RequestParam(value="quantity", defaultValue = "0") String quantityString){
        try{
            itemList.changeQuantity(idString,quantityString,inventoryRepository);
        }
        catch (InventoryException e)
        {
            System.out.println("Error: "+e.getMessage());
            return itemList;
        }
        System.out.println(itemList);
        return itemList;
    }

    @GetMapping
    public InventoryList getInventory(){
        if(itemList!=null)
            return itemList;
        Iterator<InventoryList> iter = inventoryRepository.inventoryListRepository.findAll().iterator();
        if(iter.hasNext()){
            itemList=iter.next();
        }
        else{
            itemList=new InventoryList();
            inventoryRepository.inventoryListRepository.save(itemList);
        }
        return itemList;
    }
    @GetMapping(path="/start")
    public InventoryList start() {
        // This returns a JSON or XML with the users
        itemList=new InventoryList(inventoryRepository);
        return itemList;
    }
    Map<Product, Integer> averageConsumptionForAll;
    public void computeAverageConsumptionForAll(){

    }
    public void suggestItems(){

    }
}
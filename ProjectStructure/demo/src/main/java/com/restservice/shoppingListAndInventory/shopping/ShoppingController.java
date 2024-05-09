package com.restservice.shoppingListAndInventory.shopping;
import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import com.restservice.shoppingListAndInventory.inventory.Quantity;
import com.restservice.shoppingListAndInventory.inventory.QuantityType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    ShoppingLists shoppingLists=new ShoppingLists(entityManager);
    public ShoppingController(){

    }
    @PostMapping("/addList")
    public ShoppingLists addList(){
        shoppingLists.addList(entityManager);
        System.out.println(shoppingLists);
        return shoppingLists;
    }
    @DeleteMapping("/removeList")
    public ShoppingLists removeList(@RequestParam(value = "index", defaultValue = "-1") String indexString){
       try{
           shoppingLists.removeList(indexString, entityManager);
       } catch (ShoppingException e){
           System.out.println("Error: " + e.getMessage());
           return shoppingLists;
       }

        System.out.println(shoppingLists);
       return shoppingLists;
    }

    @PostMapping("/addItem")
    public ShoppingLists addItem (@RequestParam(value="index", defaultValue = "-1") String indexString,
                         @RequestParam(value = "name", defaultValue = "____") String name,
                         @RequestParam(value = "quantity", defaultValue = "1") String quantityString,
                         @RequestParam(value="price", defaultValue = "0") String priceString ){


        try{
            shoppingLists.addItem(indexString, name, quantityString, priceString, entityManager);
        } catch (ShoppingException e){
            System.out.println("Error: " + e.getMessage());
            return shoppingLists;
        }
        System.out.println(shoppingLists);
        return shoppingLists;
    }

    @DeleteMapping("/removeItem")
    public ShoppingLists removeItem(@RequestParam(value="index", defaultValue = "-1") String indexString,
                           @RequestParam(value="id", defaultValue = "-1") String idString){
        try{
            shoppingLists.removeItem(indexString, idString, entityManager);
        } catch (ShoppingException e){
            System.out.println("Error: " + e.getMessage());
            return shoppingLists;
        }
        System.out.println(shoppingLists);
        return shoppingLists;
    }
    @PatchMapping("/changeQuantity")
    public ShoppingLists changeQuantity(@RequestParam(value="index", defaultValue = "-1") String indexString,
                               @RequestParam(value="id", defaultValue = "-1") String idString,
                               @RequestParam(value="quantity", defaultValue = "0") String quantityString){

        try{
            shoppingLists.changeQuantity(indexString, idString, quantityString, entityManager);
        } catch (ShoppingException e){
            System.out.println("Error: " + e.getMessage());
            return shoppingLists;
        }
        System.out.println(shoppingLists);
        return shoppingLists;

    }
    @PostMapping("/autocomplete")
    public ShoppingLists autocomplete(@RequestParam(value="name", defaultValue = "") String name){
        try{
            shoppingLists.autocomplete(name);
        } catch (ShoppingException e){
            System.out.println("Error: " + e.getMessage());
        }
        return shoppingLists;
    }
    @PatchMapping("/changePrice")
    public ShoppingLists changePrice(@RequestParam(value = "index", defaultValue = "-1") String indexString,
                                     @RequestParam(value="id", defaultValue = "-1") String idString,
                                     @RequestParam(value="price", defaultValue = "0") String priceString){
        try{
            shoppingLists.changePrice(indexString, idString, priceString);
        } catch (ShoppingException e){
            System.out.println("Error: " + e.getMessage());
            return shoppingLists;
        }
        System.out.println(shoppingLists);
        return shoppingLists;
    }
    @GetMapping
    public ShoppingLists getShoppingLists(){
        return shoppingLists;
    }

}

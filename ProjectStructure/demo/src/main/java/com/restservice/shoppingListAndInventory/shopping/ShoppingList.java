package com.restservice.shoppingListAndInventory.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.restockAndShoppingOptimization.EfficientRoute;
import com.restservice.shoppingListAndInventory.inventory.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "shopping_list")
@JsonIgnoreProperties(value = {"id", "list", "route"})
public class ShoppingList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<ShoppingItem> shoppingList;

    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private ShoppingLists list;

    @Transient
    EfficientRoute route;
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
    public void addItem(String name, Quantity quantity, float price, ShoppingRepository shoppingRepository) throws ShoppingException {
        if(name.isEmpty())
            throw new ShoppingException("Item name cannot be empty.");
        if(quantity.getValue()<0&&quantity.getValue()!=-1)
            throw new ShoppingException("Quantity cannot be negative.");
        if(price < 0)
            throw new ShoppingException("Price cannot be nagative.");
        ShoppingItem item= new ShoppingItem(name, quantity, price);
        item.setList(this);
        shoppingList.add(item);

        shoppingRepository.shoppingItemRepository.save(item);
    }
    public void addItem(String name, String quantityString, String priceString, ShoppingRepository shoppingRepository) throws ShoppingException{
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
        this.addItem(name, new Quantity(quantity, QuantityType.Amount), price, shoppingRepository);
    }
    public ShoppingItem removeItem(String idString, ShoppingRepository shoppingRepository) throws ShoppingException{
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        }
        return this.removeItem(id, shoppingRepository);
    }
    public ShoppingItem removeItem(int id, ShoppingRepository shoppingRepository) throws ShoppingException{
        if(id<0)
            throw new ShoppingException("Item ID has to be a non-negative integer.");
        if(id>=shoppingList.size())
            throw new ShoppingException("Item ID cannot be bigger that the list's size.");
        ShoppingItem item = shoppingList.get(id);
        shoppingList.remove(id);
        shoppingRepository.shoppingItemRepository.delete(item);
        return item;
    }
    public void setItemDetails(String idString, String name, String quantityString, ShoppingRepository shoppingRepository) throws ShoppingException{
        int id;
        try{
            id=Integer.parseInt(idString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        if (id<0)
            throw new ShoppingException("Item id cannot be negative!");
        if (id > shoppingList.size() - 1)
            throw new ShoppingException("Item id cannot be bigger than list size!");
        this.changeQuantity(idString, quantityString, shoppingRepository);
        shoppingList.get(id).getItem().setName(name);
        shoppingRepository.shoppingItemRepository.save(shoppingList.get(id));
    }
    public void changeQuantity(String idString, String quantityString, ShoppingRepository shoppingRepository) throws ShoppingException{
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
        this.changeQuantity(id, new Quantity(quantity, QuantityType.Amount), shoppingRepository);
    }
    public void changeQuantity(int id, Quantity quantity, ShoppingRepository shoppingRepository) throws ShoppingException{
        if(id<0||id>shoppingList.size()-1)
            throw new ShoppingException("Item ID has to be a non-negative integer and cannot be bigger that the list's size.");
        shoppingList.get(id).setQuantity(quantity);
        shoppingRepository.shoppingItemRepository.save(shoppingList.get(id));
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

    @Override
    public String toString() {
        return "ShoppingList{" +
                "shoppingList=" + shoppingList +
                '}';
    }
}

package com.restservice.shoppingListAndInventory.shopping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "shopping_lists")
@JsonIgnoreProperties(value = {"id", "household"})
public class ShoppingLists {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id=1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "household_id", referencedColumnName = "id")
    private Household household;

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<ShoppingList> shoppingLists=new ArrayList<>();

    public ShoppingLists(String name, ShoppingRepository shoppingRepository){
        this.addList(name, shoppingRepository);
    }

    public ShoppingLists(){
    }

    private static List<ShoppingList> loadAllData(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ShoppingList> criteria = builder.createQuery(ShoppingList.class);
        criteria.from(ShoppingList.class);
        return session.createQuery(criteria).getResultList();
    }
    public void addList(String name, ShoppingRepository shoppingRepository){
        ShoppingList list=new ShoppingList(name);
        list.setList(this);
        shoppingLists.add(list);
        shoppingRepository.shoppingListRepository.save(list);
    }
    public void removeList(int index, ShoppingRepository shoppingRepository) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingRepository.shoppingListRepository.delete(shoppingLists.get(index));
        shoppingLists.remove(index);
    }
    public void removeList(String indexString, ShoppingRepository shoppingRepository) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        if(shoppingLists.isEmpty())
            throw new ShoppingException("There are no lists to delete!");
        this.removeList(index, shoppingRepository);
    }
    public void addItem(int index, String idString, String quantityString, String priceString, HouseholdRepositoriesGroup repositories)throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).addItem(idString, quantityString, priceString, repositories);
    }
    public void addItem(String indexString, String idString, String quantityString, String priceString, HouseholdRepositoriesGroup repositories) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.addItem(index, idString, quantityString, priceString, repositories);
    }
    public ShoppingItem removeItem(int index, String idString, ShoppingRepository shoppingRepository) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        return shoppingLists.get(index).removeItem(idString, shoppingRepository);
    }
    public ShoppingItem removeItem(String indexString, String idString, ShoppingRepository shoppingRepository) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        return this.removeItem(index, idString, shoppingRepository);
    }
    public void setItemDetails(String indexString, String idString, String name, String quantityString, ShoppingRepository shoppingRepository) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).setItemDetails(idString,  name,  quantityString,  shoppingRepository);
    }
    public void changeQuantity(int index, String idString, String quantityString, ShoppingRepository shoppingRepository) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).changeQuantity(idString, quantityString, shoppingRepository);
    }
    public void changeQuantity(String indexString, String idString, String quantityString, ShoppingRepository shoppingRepository) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.changeQuantity(index, idString, quantityString, shoppingRepository);
    }

    public void changePrice(int index, String idString, String priceString) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).changePrice(idString, priceString);
    }
    public void changePrice(String indexString, String idString, String quantityString) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.changePrice(index, idString, quantityString);
    }
    public void autocomplete(String name) throws ShoppingException{
        if(name.isEmpty()){
            throw new ShoppingException("Cannot search with empty name");
        }
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

    @Override
    public String toString() {
        return "ShoppingLists{" +
                "shoppingLists=" + shoppingLists +
                '}';
    }
}

package com.restservice.shoppingListAndInventory.shopping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@NoArgsConstructor
@Table(name = "shopping_lists")
@JsonIgnoreProperties(value = {"id"})
public class ShoppingLists {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id=1;

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<ShoppingList> shoppingLists=new ArrayList<>();;
    public ShoppingLists(EntityManager entityManager){
        Session session = entityManager.unwrap(Session.class);
        shoppingLists=loadAllData(session);
        if(shoppingLists.isEmpty())
            this.addList(entityManager);
        entityManager.getTransaction().begin();
        entityManager.merge(this);
        entityManager.merge(shoppingLists.get(0));
        entityManager.getTransaction().commit();
    }

    private static List<ShoppingList> loadAllData(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ShoppingList> criteria = builder.createQuery(ShoppingList.class);
        criteria.from(ShoppingList.class);
        return session.createQuery(criteria).getResultList();
    }
    public void addList(EntityManager entityManager){
        ShoppingList list=new ShoppingList();
        list.setList(this);
        shoppingLists.add(list);

        entityManager.getTransaction().begin();
        entityManager.merge(list);
        entityManager.getTransaction().commit();
    }
    public void removeList(int index, EntityManager entityManager) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(shoppingLists.get(index)) ? shoppingLists.get(index) : entityManager.merge(shoppingLists.get(index)));
        entityManager.getTransaction().commit();
        shoppingLists.remove(index);
    }
    public void removeList(String indexString, EntityManager entityManager) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        if(shoppingLists.isEmpty())
            throw new ShoppingException("There are no lists to delete!");
        this.removeList(index, entityManager);
    }
    public void addItem(int index, String name, String quantityString, String priceString, EntityManager entityManager)throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).addItem(name, quantityString, priceString, entityManager);
    }
    public void addItem(String indexString, String name, String quantityString, String priceString, EntityManager entityManager) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.addItem(index, name, quantityString, priceString, entityManager);
    }
    public void removeItem(int index, String idString, EntityManager entityManager) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).removeItem(idString, entityManager);
    }
    public void removeItem(String indexString, String idString, EntityManager entityManager) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.removeItem(index, idString, entityManager);
    }
    public void changeQuantity(int index, String idString, String quantityString, EntityManager entityManager) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).changeQuantity(idString, quantityString, entityManager);
    }
    public void changeQuantity(String indexString, String idString, String quantityString, EntityManager entityManager) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.changeQuantity(index, idString, quantityString, entityManager);
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


}

package com.restservice.shoppingListAndInventory.shopping;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
public class ShoppingLists {
    List<ShoppingList> shoppingLists;
    public ShoppingLists(){ shoppingLists=new ArrayList<>();
    shoppingLists.add(new ShoppingList());}
    public void addList(){
        shoppingLists.add(new ShoppingList());
    }
    public void removeList(int index) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.remove(index);
    }
    public void removeList(String indexString) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        if(shoppingLists.isEmpty())
            throw new ShoppingException("There are no lists to delete!");
        this.removeList(index);
    }
    public void addItem(int index, String name, String quantityString, String priceString)throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1){
            System.out.println(shoppingLists.size()-1);
            throw new ShoppingException("List index cannot be bigger than list size!");}
        shoppingLists.get(index).addItem(name, quantityString, priceString);
    }
    public void addItem(String indexString, String name, String quantityString, String priceString) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.addItem(index, name, quantityString, priceString);
    }
    public void removeItem(int index, String idString) throws ShoppingException{
        if (index<0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).removeItem(idString);
    }
    public void removeItem(String indexString, String idString) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.removeItem(index, idString);
    }
    public void changeQuantity(int index, String idString, String quantityString) throws ShoppingException{
        if (index < 0)
            throw new ShoppingException("List index cannot be negative!");
        if (index > shoppingLists.size() - 1)
            throw new ShoppingException("List index cannot be bigger than list size!");
        shoppingLists.get(index).changeQuantity(idString, quantityString);
    }
    public void changeQuantity(String indexString, String idString, String quantityString) throws ShoppingException{
        int index;
        try{
            index=Integer.parseInt(indexString);
        } catch (NumberFormatException e){
            throw new ShoppingException("Index has to be a number.");
        }
        this.changeQuantity(index, idString, quantityString);
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

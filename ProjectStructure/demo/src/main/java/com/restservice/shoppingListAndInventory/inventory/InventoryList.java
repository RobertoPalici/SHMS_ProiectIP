package com.restservice.shoppingListAndInventory.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restservice.household.Household;
import com.restservice.shoppingListAndInventory.chores.ChoreHistoryItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventory_list")
@JsonIgnoreProperties(value = {"id", "itemBuyHistoryList", "household"})
public class InventoryList {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int id = 1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "household_id", referencedColumnName = "id")
    private Household household;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "list", cascade=CascadeType.ALL, orphanRemoval=true)
    List<InventoryItem> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "list")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<InventoryItem> itemBuyHistoryList = new ArrayList<>();

    @Transient
    private boolean is_persisted = false;

    public InventoryList(InventoryRepository inventoryRepository) {
        for (InventoryItem item : inventoryRepository.inventoryItemRepository.findAll()) {
            itemList.add(item);
        }
    }

    private static List<InventoryItem> loadAllData(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<InventoryItem> criteria = builder.createQuery(InventoryItem.class);
        criteria.from(InventoryItem.class);
        return session.createQuery(criteria).getResultList();
    }

    public int findItemIndex(String name) {
        for (int i = 0; i < itemList.size(); i++) {
            if (Objects.equals(itemList.get(i).getItem().getName(), name)) {
                //itemList.get(i).getItem().addQuantity(quantity);
                return i;
            }
        }
        return -1;
    }

    public InventoryItem getItemAt(int index) {
        return itemList.get(index);
    }

    public void addItem(InventoryItem item, InventoryRepository inventoryRepository) {
        item.setList(this);
        inventoryRepository.inventoryItemRepository.save(item);
        itemList.add(item);
    }

    public void addItem(int id, Quantity quantity, InventoryRepository inventoryRepository) throws InventoryException {
        if (quantity.value < 0 && quantity.value != -1)
            throw new InventoryException("Quantity cannot be negative.");
        if (id<0)
            throw new InventoryException("Id cannot be negative.");
        InventoryItem item = new InventoryItem(id, quantity, inventoryRepository);
        item.setList(this);
        inventoryRepository.inventoryItemRepository.save(item);
        itemList.add(item);
    }

    public void addItem(String idString, String quantityString, InventoryRepository inventoryRepository) throws InventoryException {
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Quantity has to be a number.");
        }
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        this.addItem(id, new Quantity(quantity, QuantityType.Amount), inventoryRepository);
    }

    public void setItemDetails(String idString, String name, String quantityString, InventoryRepository inventoryRepository) throws InventoryException {
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        this.changeQuantity(idString, quantityString, inventoryRepository);
        itemList.get(id).getItem().setName(name);
        inventoryRepository.inventoryItemRepository.save(itemList.get(id));
    }

    public void removeItem(String idString, InventoryRepository inventoryRepository) throws InventoryException {
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        this.removeItem(id, inventoryRepository);
    }

    public void removeItem(int id, InventoryRepository inventoryRepository) throws InventoryException {
        if (id < 0)
            throw new InventoryException("Item ID has to be a non-negative integer.");
        if (id >= itemList.size())
            throw new InventoryException("Item ID cannot be bigger that the list's size.");
        InventoryItem item=itemList.remove(id);
        item.setList(null);
        inventoryRepository.inventoryItemRepository.delete(item);
    }

    public void changeQuantity(String idString, String quantityString, InventoryRepository inventoryRepository) throws InventoryException {
        float quantity;
        try {
            quantity = Float.parseFloat(quantityString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Quantity has to be a number.");
        }
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InventoryException("Item ID has to be a non-negative integer.");
        }
        changeQuantity(id, new Quantity(quantity, QuantityType.Amount), inventoryRepository);
    }

    public void changeQuantity(int id, Quantity quantity, InventoryRepository inventoryRepository) throws InventoryException {
        if (id < 0 || id > itemList.size() - 1)
            throw new InventoryException("Item ID has to be a non-negative integer and cannot be bigger that the list's size.");

        itemList.get(id).setQuantity(quantity);
        inventoryRepository.inventoryItemRepository.save(itemList.get(id));
    }

    public void sortItems(String filter, InventoryRepository inventoryRepository) throws InventoryException {
        for (int i = 0; i < itemList.size() - 1; i++)
            for (int j = i + 1; j < itemList.size(); j++)
                if (sortFilterCondition(filter, itemList.get(i), itemList.get(j))) {
                    InventoryItem temp = itemList.get(i);
                    itemList.set(i, itemList.get(j));
                    itemList.set(j, temp);
                }
    }

    public boolean sortFilterCondition(String filter, InventoryItem item1, InventoryItem item2) throws InventoryException {
        return switch (filter) {
            case "0" -> item1.getItem().getName().compareToIgnoreCase(item2.getItem().getName()) > 0;
            case "1" -> item1.getItem().getName().compareToIgnoreCase(item2.getItem().getName()) < 0;
            case "2" -> item1.getQuantity().getValue() > item2.getQuantity().getValue();
            case "3" -> item1.getQuantity().getValue() < item2.getQuantity().getValue();
            default -> throw new InventoryException("Sort filter unknown");
        };
    }

    @Override
    public String toString() {
        return "InventoryList{" +
                "itemList=" + itemList +
                '}';
    }
}

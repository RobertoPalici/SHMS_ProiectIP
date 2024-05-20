package com.restservice.shoppingListAndInventory.shopping;

import com.restservice.shoppingListAndInventory.inventory.InventoryList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Repository
@Service
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Integer> {
}

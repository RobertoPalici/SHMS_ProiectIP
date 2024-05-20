package com.restservice.shoppingListAndInventory.inventory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Repository
@Service
public interface InventoryListRepository extends CrudRepository<InventoryList, Integer> {
}

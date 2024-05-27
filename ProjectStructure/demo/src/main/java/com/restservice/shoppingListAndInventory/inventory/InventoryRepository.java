package com.restservice.shoppingListAndInventory.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryRepository {
    @Autowired
    public InventoryListRepository inventoryListRepository;
    @Autowired
    public InventoryItemRepository inventoryItemRepository;
    @Autowired
    public ProductRepository productRepository;
    //@Autowired
    //public ProductRepository_temporary productRepository_temporary;
}
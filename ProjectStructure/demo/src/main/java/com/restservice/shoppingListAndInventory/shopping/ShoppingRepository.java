package com.restservice.shoppingListAndInventory.shopping;

import com.restservice.shoppingListAndInventory.inventory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingRepository {
    @Autowired
    public ShoppingListRepository shoppingListRepository;
    @Autowired
    public ShoppingListsRepository shoppingListsRepository;
    @Autowired
    public ShoppingItemRepository shoppingItemRepository;
    @Autowired
    public ProductRepository productRepository;
}
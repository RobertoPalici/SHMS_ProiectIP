package com.restservice.shoppingListAndInventory.chores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChoreRepository {
    @Autowired
    public ChoreListRepository choreListRepository;
    @Autowired
    public ChoreItemRepository choreItemRepository;
    @Autowired
    public ChoreHistoryItemRepository choreHistoryItemRepository;
    @Autowired
    public ChoreHistoryListRepository choreHistoryListRepository;
}

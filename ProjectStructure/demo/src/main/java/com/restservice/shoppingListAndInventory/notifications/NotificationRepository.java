package com.restservice.shoppingListAndInventory.notifications;

import com.restservice.shoppingListAndInventory.chores.ChoreItemRepository;
import com.restservice.shoppingListAndInventory.chores.ChoreListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationRepository {
    @Autowired
    public NotificationItemRepository notificationItemRepository;
    @Autowired
    public NotificationListRepository notificationListRepository;
}

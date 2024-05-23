package com.restservice.shoppingListAndInventory.notifications;

import com.restservice.shoppingListAndInventory.chores.Chore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Repository
@Service
public interface NotificationItemRepository extends CrudRepository<Notification, Integer> {
}

package com.restservice.shoppingListAndInventory.notifications;

import com.restservice.household.Household;
import com.restservice.household.HouseholdRepositoriesGroup;
import com.restservice.shoppingListAndInventory.chores.ChoresHistoryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private HouseholdRepositoriesGroup repositories;
    //Household household;
    @DeleteMapping("/clearNotifications")
    public NotificationsList clearNotifications() {
        Household household=Household.loadHousehold(repositories);
        household.notificationsList.clearNotifications(repositories.notificationRepository);
        System.out.println(household.notificationsList);
        return household.notificationsList;
    }
    @GetMapping
    public NotificationsList getNotifications(){
        Household household=Household.loadHousehold(repositories);
        System.out.println(household.notificationsList);
        return household.notificationsList;
    }
}

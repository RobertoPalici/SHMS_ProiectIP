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
    Household household;
    @DeleteMapping("/clearNotifications")
    public NotificationsList clearNotifications() {
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            household = iter.next();
        } else {
            household = new Household(repositories);
        }
        household.notificationsList.clearNotifications(repositories.notificationRepository);
        System.out.println(household.notificationsList);
        return household.notificationsList;
    }
    @GetMapping
    public NotificationsList getNotifications(){
//        if (household != null)
//            return household.notificationsList;
        Iterator<Household> iter = repositories.householdRepository.findAll().iterator();
        if (iter.hasNext()) {
            household = iter.next();
        } else {
            household = new Household(repositories);
        }
        NotificationsList temp = household.notificationsList;
        System.out.println("Before clear: " + household.notificationsList);
        household.notificationsList.clearNotifications(repositories.notificationRepository);
        System.out.println("After clear: " + household.notificationsList);
        return temp;
    }
}

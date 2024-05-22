package com.restservice.household;

import com.restservice.household.Household;
import com.restservice.shoppingListAndInventory.inventory.InventoryItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Repository
@Service
public interface HouseholdRepository extends CrudRepository<Household, Integer> {
}

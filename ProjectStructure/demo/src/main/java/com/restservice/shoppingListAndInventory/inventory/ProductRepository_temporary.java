package com.restservice.shoppingListAndInventory.inventory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Repository
@Service
public interface ProductRepository_temporary extends CrudRepository<Product_Temporary, Integer> {
}

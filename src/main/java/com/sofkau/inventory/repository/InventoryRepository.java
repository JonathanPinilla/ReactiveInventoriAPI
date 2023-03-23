package com.sofkau.inventory.repository;

import com.sofkau.inventory.domain.collection.Armor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends ReactiveMongoRepository<Armor, String> {

}

package com.sofkau.inventory.usecases;

import com.sofkau.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class DeleteArmorUseCase implements Function<String, Mono<Void>> {

    private final InventoryRepository inventoryRepository;

    public DeleteArmorUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Override
    public Mono<Void> apply(String id) {
        return inventoryRepository.deleteById(id);
    }
}

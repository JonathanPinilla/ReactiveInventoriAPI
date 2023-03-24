package com.sofkau.inventory.usecases;

import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.dto.ArmorDTO;
import com.sofkau.inventory.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;


@Service
public class GetAllArmorsUseCase implements Supplier<Flux<ArmorDTO>> {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public GetAllArmorsUseCase(InventoryRepository inventoryRepository, ModelMapper modelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<ArmorDTO> get() {
        return inventoryRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto)
                .onErrorResume(Mono::error);
    }

    private ArmorDTO toDto(Armor player) {
        return modelMapper.map(player, ArmorDTO.class);
    }
}

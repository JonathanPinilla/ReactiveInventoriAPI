package com.sofkau.inventory.usecases;

import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.dto.ArmorDTO;
import com.sofkau.inventory.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class GetArmorByIdUseCase implements Function<String, Mono<ArmorDTO>> {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public GetArmorByIdUseCase(InventoryRepository inventoryRepository, ModelMapper modelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Mono<ArmorDTO> apply(String id) {
        return inventoryRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    private ArmorDTO toDto(Armor player) {
        return modelMapper.map(player, ArmorDTO.class);
    }
}

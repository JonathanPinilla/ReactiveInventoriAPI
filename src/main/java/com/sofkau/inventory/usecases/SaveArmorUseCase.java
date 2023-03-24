package com.sofkau.inventory.usecases;

import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.dto.ArmorDTO;
import com.sofkau.inventory.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class SaveArmorUseCase implements Function<ArmorDTO, Mono<ArmorDTO>> {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public SaveArmorUseCase(InventoryRepository inventoryRepository, ModelMapper modelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<ArmorDTO> apply(ArmorDTO armorDTO) {
        return inventoryRepository.save(toEntity(armorDTO))
                .map(this::toDto)
                .onErrorResume(Mono::error);
    }

    private Armor toEntity(ArmorDTO playerDTO) {
        return modelMapper.map(playerDTO, Armor.class);
    }

    private ArmorDTO toDto(Armor player) {
        return modelMapper.map(player, ArmorDTO.class);
    }

}

package com.sofkau.inventory.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.dto.ArmorDTO;
import com.sofkau.inventory.publisher.ArmorEquippedPublisher;
import com.sofkau.inventory.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EquipArmorUseCase {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final ArmorEquippedPublisher armorEquippedPublisher;

    public EquipArmorUseCase(InventoryRepository inventoryRepository, ModelMapper modelMapper, ArmorEquippedPublisher armorEquippedPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
        this.armorEquippedPublisher = armorEquippedPublisher;
    }

    public Mono<Armor> equipArmor(String armorId, String playerId){
        return inventoryRepository.findById(armorId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("The armor does not exist")))
                .flatMap(armor -> {
                    if (armor.getIsEquipped()) {
                        return Mono.error(new IllegalArgumentException("The armor is already equipped"));
                    }else {
                        armor.setIsEquipped(true);
                        return this.inventoryRepository.save(armor);
                    }
                })
                .doOnSuccess(armor -> {
                    try {
                        armorEquippedPublisher.publishEquip(toArmorDTO(armor), playerId);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnError(armorEquippedPublisher::publishError);
    }

    public ArmorDTO toArmorDTO(Armor armor){
        return modelMapper.map(armor, ArmorDTO.class);
    }

}

package com.sofkau.inventory.usecases;

import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.collection.ArmorType;
import com.sofkau.inventory.publisher.ArmorEquippedPublisher;
import com.sofkau.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EquipArmorUseCaseTest {

    @Mock
    private InventoryRepository inventoryRepository;

    private EquipArmorUseCase equipArmorUseCase;
    private ModelMapper modelMapper;

    @Mock
    private ArmorEquippedPublisher armorEquippedPublisher;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        equipArmorUseCase = new EquipArmorUseCase(inventoryRepository, modelMapper, armorEquippedPublisher);
    }

    @Test
    void equipArmor() {

        Armor armor = new Armor();
        armor.setId("1");
        armor.setArmorType(ArmorType.BOOTS);
        armor.setArmorFamily("family");
        armor.setArmor(10.0);
        armor.setDamage(5.0);
        armor.setHealth(100.0);
        armor.setMana(50.0);
        armor.setSpeed(20.0);
        armor.setIsEquipped(false);

        String playerId = "2";

        Mockito.when(inventoryRepository.findById("1")).thenReturn(Mono.just(armor));
        Mockito.when(inventoryRepository.save(armor)).thenReturn(Mono.just(armor));

        var result = equipArmorUseCase.equipArmor("1", playerId);

        StepVerifier.create(result)
                .expectNext(armor)
                .verifyComplete();
    }
}
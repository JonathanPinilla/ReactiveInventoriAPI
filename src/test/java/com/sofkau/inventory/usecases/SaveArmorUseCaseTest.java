package com.sofkau.inventory.usecases;

import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.collection.ArmorType;
import com.sofkau.inventory.domain.dto.ArmorDTO;
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

@ExtendWith(MockitoExtension.class)
class SaveArmorUseCaseTest {

    @Mock
    private InventoryRepository inventoryRepository;
    private SaveArmorUseCase saveArmorUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup(){
        modelMapper = new ModelMapper();
        saveArmorUseCase = new SaveArmorUseCase(inventoryRepository, modelMapper);
    }

    @Test
    void testSaveArmor() {

        // create a test armor
        Armor armor = new Armor();
        armor.setId("1");
        armor.setArmorType(ArmorType.CHEST);
        armor.setArmorFamily("family");
        armor.setArmor(10.0);
        armor.setDamage(5.0);
        armor.setHealth(100.0);
        armor.setMana(50.0);
        armor.setSpeed(20.0);

        // mock the inventory repository
        Mockito.when(inventoryRepository.save(Mockito.any(Armor.class))).thenReturn(Mono.just(armor));

        // save the armor using the use case
        var result = saveArmorUseCase.apply(toDto(armor));

        // verify that the armor was saved
        StepVerifier.create(result)
                .expectNext(toDto(armor))
                .verifyComplete();

    }

    private ArmorDTO toDto(Armor armor) {
        return modelMapper.map(armor, ArmorDTO.class);
    }

}
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllArmorsUseCaseTest {

    @Mock
    private InventoryRepository inventoryRepository;
    private GetAllArmorsUseCase getAllArmorsUseCase;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup(){
        modelMapper = new ModelMapper();
        getAllArmorsUseCase = new GetAllArmorsUseCase(inventoryRepository, modelMapper);
    }

    @Test
    void testGetAllArmors() {

        Armor armor = new Armor();
        armor.setId("1");
        armor.setArmorType(ArmorType.CHEST);
        armor.setArmorFamily("family");
        armor.setArmor(10.0);
        armor.setDamage(5.0);
        armor.setHealth(100.0);
        armor.setMana(50.0);
        armor.setSpeed(20.0);

        Mockito.when(inventoryRepository.findAll()).thenReturn(Flux.just(armor));

        var result = getAllArmorsUseCase.get();

        StepVerifier.create(result)
                .expectNext(toDto(armor))
                .verifyComplete();

    }

    private ArmorDTO toDto(Armor armor) {
        return modelMapper.map(armor, ArmorDTO.class);
    }

}
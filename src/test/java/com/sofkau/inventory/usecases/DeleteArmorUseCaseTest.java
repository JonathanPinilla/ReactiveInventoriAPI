package com.sofkau.inventory.usecases;

import com.sofkau.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DeleteArmorUseCaseTest {

    @Mock
    private InventoryRepository inventoryRepository;
    private DeleteArmorUseCase deleteArmorUseCase;

    @BeforeEach
    public void setup(){
        deleteArmorUseCase = new DeleteArmorUseCase(inventoryRepository);
    }

    @Test
    void testDeleteArmor() {
        // create a test armor ID
        String armorId = "1234";

        // mock the inventory repository
        Mockito.when(inventoryRepository.deleteById(armorId)).thenReturn(Mono.empty());

        // delete the armor using the use case
        Mono<Void> result = deleteArmorUseCase.apply(armorId);

        // verify that the armor was deleted
        StepVerifier.create(result)
                .expectComplete()
                .verify();

    }

}

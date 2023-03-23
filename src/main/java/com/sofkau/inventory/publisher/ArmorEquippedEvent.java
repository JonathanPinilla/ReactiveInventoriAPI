package com.sofkau.inventory.publisher;

import com.sofkau.inventory.domain.dto.ArmorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArmorEquippedEvent {

    private String playerId;
    private ArmorDTO armorEquipped;
    private String typeEvent;

}

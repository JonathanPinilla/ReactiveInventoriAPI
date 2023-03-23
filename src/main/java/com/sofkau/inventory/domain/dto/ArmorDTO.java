package com.sofkau.inventory.domain.dto;

import com.sofkau.inventory.domain.collection.ArmorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArmorDTO {

    private String id;
    private ArmorType armorType;
    private String armorFamily;
    private Double armor;
    private Double damage;
    private Double health;
    private Double mana;
    private Double speed;
    private Boolean isEquipped;

}

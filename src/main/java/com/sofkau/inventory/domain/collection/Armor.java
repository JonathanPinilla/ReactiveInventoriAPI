package com.sofkau.inventory.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "armor")
@AllArgsConstructor
@NoArgsConstructor
public class Armor {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);
    private ArmorType armorType;
    private String armorFamily;
    private Double armor;
    private Double damage;
    private Double health;
    private Double mana;
    private Double speed;
    private Boolean isEquipped = false;

}

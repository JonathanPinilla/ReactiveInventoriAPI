package com.sofkau.inventory.domain.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "The name of the armor is required")
    private ArmorType armorType;

    @NotNull(message = "The family of the armor is required")
    @NotBlank(message = "The family of the armor should not be empty")
    private String armorFamily;

    @NotNull(message = "The armor value is required")
    private Double armor;

    @NotNull(message = "The damage value is required")
    private Double damage;

    @NotNull(message = "The health value is required")
    private Double health;

    @NotNull(message = "The mana value is required")
    private Double mana;

    @NotNull(message = "The speed value is required")
    private Double speed;

    private Boolean isEquipped = false;

}

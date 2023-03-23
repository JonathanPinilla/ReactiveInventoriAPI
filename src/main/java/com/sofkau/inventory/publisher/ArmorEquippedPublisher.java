package com.sofkau.inventory.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofkau.inventory.domain.dto.ArmorDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArmorEquippedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ArmorEquippedPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishEquip(ArmorDTO armorDTO, String playerId) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new ArmorEquippedEvent(playerId, armorDTO, "ArmorEquipped"));
        rabbitTemplate.convertAndSend("armor-exchange-events", "events.armor.routing.key", message);
    }

    public void publishUnEquip(ArmorDTO armorDTO, String playerId) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new ArmorEquippedEvent(playerId, armorDTO, "ArmorUnEquipped"));
        rabbitTemplate.convertAndSend("armor-exchange-events", "events.armor.routing.key", message);
    }

    public void publishError(Throwable errorEvent){
    }

}

package com.sofkau.inventory.router;

import com.sofkau.inventory.domain.collection.Armor;
import com.sofkau.inventory.domain.dto.ArmorDTO;
import com.sofkau.inventory.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class InventoryRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllArmor(GetAllArmorsUseCase getAllArmorsUseCase) {
        return route(GET("/api/armor"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(getAllArmorsUseCase.get(), Armor.class)
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getArmorById(GetArmorByIdUseCase getArmorByIdUseCase) {
        return route(GET("/api/armor/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(getArmorByIdUseCase.apply(request.pathVariable("id")), Armor.class)
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveArmor(SaveArmorUseCase saveArmorUseCase) {
        return route(POST("/api/armor").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ArmorDTO.class)
                        .flatMap(armorDTO -> saveArmorUseCase.apply(armorDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> equipArmor(EquipArmorUseCase equipArmorUseCase) {
        return route(PUT("/api/armor/{id}/equip/{id-p}"),
                request -> request.bodyToMono(ArmorDTO.class)
                        .flatMap(armorDTO -> equipArmorUseCase.equipArmor(request.pathVariable("id"), request.pathVariable("id-p"))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteArmor(DeleteArmorUseCase deleteArmorUseCase) {
        return route(DELETE("/api/armor/{id}"),
                request -> deleteArmorUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Armor deleted successfully"))
                        .onErrorResume(error -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Collections.singletonMap("error", error.getMessage()))
                        ));
    }

}

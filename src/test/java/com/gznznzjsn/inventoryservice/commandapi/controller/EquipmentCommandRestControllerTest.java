package com.gznznzjsn.inventoryservice.commandapi.controller;

import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCmdService;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@WebFluxTest(EquipmentCommandRestController.class)
class EquipmentCommandRestControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private EquipmentCmdService service;

    @Test
    public void create() {
        var command = new EquipmentCreateCommand(
                UUID.fromString(
                        "d4704dcf-0217-4215-b1fa-3f877e743911"
                ),
                null,
                "FAKE NAME",
                "FAKE MANUFACTURER",
                "FAKE DESCRIPTION"
        );
        Mockito.when(service.create(any(EquipmentCreateCommand.class)))
                .thenReturn(Mono.empty());

        client.post()
                .uri(String.format(
                        "/inventory-api/v1/inventories/%s/equipment",
                        command.getInventoryId()
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        Equipment.builder()
                                .id(command.getInventoryId())
                                .name(command.getName())
                                .manufacturer(command.getManufacturer())
                                .description(command.getDescription())
                                .build()
                ))
                .exchange()
                .expectStatus().isCreated();

        verify(service).create(command);
    }

}
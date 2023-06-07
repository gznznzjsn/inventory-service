package com.gznznzjsn.inventoryservice.commandapi.controller;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.RequirementCmdService;
import com.gznznzjsn.inventoryservice.core.model.Requirement;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
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
@WebFluxTest(RequirementCommandRestController.class)
class RequirementCommandRestControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private RequirementCmdService service;

    @Test
    public void create() {
        var command = new RequirementCreateCommand(
                UUID.fromString(
                        "d4704dcf-0217-4215-b1fa-3f877e743911"
                ),
                null,
                Specialization.CLEANER,
                "FAKE NAME"
        );
        Mockito.when(service.create(any(RequirementCreateCommand.class)))
                .thenReturn(Mono.empty());

        client.post()
                .uri(String.format(
                        "/inventory-api/v1/inventories/%s/requirements",
                        command.getInventoryId()
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        Requirement.builder()
                                .id(command.getInventoryId())
                                .specialization(command.getSpecialization())
                                .name(command.getName())
                                .build()
                ))
                .exchange()
                .expectStatus().isCreated();

        verify(service).create(command);
    }

}
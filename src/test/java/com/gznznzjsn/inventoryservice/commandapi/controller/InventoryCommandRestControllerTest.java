package com.gznznzjsn.inventoryservice.commandapi.controller;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.InventoryCmdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@WebFluxTest(InventoryCommandRestController.class)
class InventoryCommandRestControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private InventoryCmdService service;

    @Test
    public void create() {
        var command = new InventoryCreateCommand(null);
        Mockito.when(service.create(any(InventoryCreateCommand.class)))
                .thenReturn(Mono.empty());

        client.post()
                .uri("/inventory-api/v1/inventories")
                .exchange()
                .expectStatus().isCreated();

        verify(service).create(command);
    }

}
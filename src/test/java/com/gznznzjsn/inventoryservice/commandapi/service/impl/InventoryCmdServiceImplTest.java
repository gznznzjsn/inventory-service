package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryCmdServiceImplTest {

    @InjectMocks
    private InventoryCmdServiceImpl service;

    @Mock
    private ReactorCommandGateway gateway;

    @Test
    public void create() {
        var command = new InventoryCreateCommand(null);
        when(gateway.send(any(InventoryCreateCommand.class)))
                .thenReturn(Mono.empty());

        Mono<UUID> result = service.create(command);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(gateway).send(assertArg(c -> {
            assertNotNull(c);
            assertNotNull(((InventoryCreateCommand) c).getInventoryId());
        }));
    }

    @Test
    public void delete() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        var command = new InventoryDeleteCommand(inventoryId);
        when(gateway.send(any(InventoryDeleteCommand.class)))
                .thenReturn(Mono.empty());

        Mono<UUID> result = service.delete(command);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(gateway).send(assertArg(c -> {
            assertNotNull(c);
            assertEquals(inventoryId, ((InventoryDeleteCommand) c).getInventoryId());
        }));
    }

}
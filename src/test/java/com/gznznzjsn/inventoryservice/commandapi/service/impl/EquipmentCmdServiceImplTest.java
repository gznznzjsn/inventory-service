package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipmentCmdServiceImplTest {

    @InjectMocks
    private EquipmentCmdServiceImpl service;

    @Mock
    private ReactorCommandGateway gateway;

    @Test
    public void create() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        UUID equipmentId = UUID.fromString(
                "69f9995e-8d8e-4148-a2fc-a554ce40e5b4"
        );
        String name = "shovel";
        String manufacturer = "Shovelini and Co";
        String description = "Fake shovel - fake description";
        var command = new EquipmentCreateCommand(
                inventoryId, equipmentId, name, manufacturer, description
        );
        when(gateway.send(any(EquipmentCreateCommand.class)))
                .thenReturn(Mono.empty());

        Mono<UUID> result = service.create(command);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(gateway).send(assertArg(c -> {
            assertNotNull(c);
            assertNotNull(((EquipmentCreateCommand) c).getEquipmentId());
        }));
    }

}

package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RequirementCmdServiceImplTest {

    @InjectMocks
    private RequirementCmdServiceImpl service;

    @Mock
    private ReactorCommandGateway gateway;

    @Test
    public void create() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        String name = "shovel";
        var specialization = Specialization.CLEANER;
        var command = new RequirementCreateCommand(
                inventoryId, null, specialization, name
        );
        Mockito.when(gateway.send(Mockito.any(RequirementCreateCommand.class)))
                .thenReturn(Mono.empty());

        Mono<UUID> result = service.create(command);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(gateway).send(Mockito.assertArg(c -> {
            assertNotNull(c);
            assertNotNull(((RequirementCreateCommand) c).getRequirementId());
        }));
    }

}

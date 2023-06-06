package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequirementCmdServiceImplTest {

    @InjectMocks
    private RequirementCmdServiceImpl service;

    @Mock
    private ReactorCommandGateway gateway;

    @Test
    public void create() {
        var inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        var name = "shovel";
        var specialization = Specialization.CLEANER;
        var command = new RequirementCreateCommand(
                inventoryId, null, specialization, name
        );
        when(gateway.send(any(RequirementCreateCommand.class)))
                .thenReturn(Mono.empty());

        var result = service.create(command);

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(gateway).send(assertArg(c -> {
            assertNotNull(c);
            assertNotNull(((RequirementCreateCommand) c).getRequirementId());
        }));
    }

}
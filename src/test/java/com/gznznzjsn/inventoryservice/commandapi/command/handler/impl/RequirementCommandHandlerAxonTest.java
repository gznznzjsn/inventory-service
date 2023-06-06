package com.gznznzjsn.inventoryservice.commandapi.command.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate;
import com.gznznzjsn.inventoryservice.commandapi.aggregate.RequirementEntity;
import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequirementCommandHandlerAxonTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    {
        fixture.registerAnnotatedCommandHandler(
                new RequirementCommandHandlerAxon(fixture.getRepository())
        );
    }

    @Test
    public void createRequirement() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID requirementId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new RequirementCreateCommand(
                        inventoryId, requirementId, Specialization.CLEANER, name
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new RequirementCreatedEvent(
                        inventoryId, requirementId, Specialization.CLEANER, name
                ))
                .expectState(a -> {
                    assertEquals(inventoryId, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(1, a.getRequirementMap().size());
                    assertEquals(
                            new RequirementEntity(
                                    requirementId, Specialization.CLEANER, name
                            ),
                            a.getRequirementMap().get(requirementId)
                    );
                });
    }

}

package com.gznznzjsn.inventoryservice.commandapi.command.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InventoryCommandHandlerAxonTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    {
        fixture.registerAnnotatedCommandHandler(
                new InventoryCommandHandlerAxon(fixture.getRepository())
        );
    }

    @Test
    public void createInventory() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        fixture.given()
                .when(new InventoryCreateCommand(inventoryId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new InventoryCreatedEvent(inventoryId))
                .expectState(a -> {
                    assertEquals(inventoryId, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    public void deleteInventory() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new InventoryDeleteCommand(inventoryId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new InventoryDeletedEvent(inventoryId))
                .expectMarkedDeleted();
    }

}

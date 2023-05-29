package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.EmployeeRequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class InventoryAggregateTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    @Test
    void handleInventoryCreateCommand() {
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        fixture.given()
                .when(new InventoryCreateCommand(id))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new InventoryCreatedEvent(id))
                .expectState(a -> {
                    assertEquals(id, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    void handleEmployeeRequirementCreateCommand() {
        UUID inventoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID requirementId = UUID.fromString(
                "123e4567-e89b-12d3-a456-426614174001"
        );
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new EmployeeRequirementCreateCommand(
                        inventoryId,
                        requirementId,
                        Specialization.CLEANER,
                        "FAKE NAME"
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EmployeeRequirementCreatedEvent(
                        inventoryId,
                        requirementId,
                        Specialization.CLEANER,
                        "FAKE NAME"
                ))
                .expectState(a -> {
                    assertEquals(inventoryId, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(1, a.getRequirementMap().size());
                    assertEquals(
                            new EmployeeRequirementEntity(
                                    requirementId,
                                    Specialization.CLEANER,
                                    "FAKE NAME"
                            ),
                            a.getRequirementMap().get(requirementId)
                    );
                });
    }

    @Test
    void handleEquipmentCreateCommand() {
        UUID inventoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID equipmentId = UUID.fromString(
                "123e4567-e89b-12d3-a456-426614174001"
        );
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new EquipmentCreateCommand(
                        inventoryId,
                        equipmentId,
                        "FAKE NAME"
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EquipmentCreatedEvent(
                        inventoryId,
                        equipmentId,
                        "FAKE NAME",
                        null
                ))
                .expectState(a -> {
                    assertEquals(inventoryId, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(1, a.getEquipmentMap().size());
                    assertEquals(
                            new EquipmentEntity(
                                    equipmentId,
                                    "FAKE NAME",
                                    null
                            ),
                            a.getEquipmentMap().get(equipmentId)
                    );
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

}
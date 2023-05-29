package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.common.command.EquipmentAssignCommand;
import com.gznznzjsn.common.event.EquipmentAssignedEvent;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InventoryAggregateTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    private final UUID id1 = UUID
            .fromString("123e4567-e89b-12d3-a456-426614174000");
    private final UUID id2 = UUID
            .fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
    private final UUID id3 = UUID
            .fromString("a2131bde-fe0d-11ed-be56-0242ac120002");
    private final UUID id4 = UUID
            .fromString("a80b16fe-fe0d-11ed-be56-0242ac120002");

    @Test
    void createInventoryExpectEvents() {
        fixture.given()
                .when(new InventoryCreateCommand(id1))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new InventoryCreatedEvent(id1));
    }

    @Test
    void createInventoryExpectState() {
        fixture.given()
                .when(new InventoryCreateCommand(id1))
                .expectState(a -> {
                    assertEquals(id1, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    void createRequirementExpectEvents() {
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(id1))
                .when(new EmployeeRequirementCreateCommand(
                        id1,
                        id2,
                        Specialization.CLEANER,
                        name
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EmployeeRequirementCreatedEvent(
                        id1,
                        id2,
                        Specialization.CLEANER,
                        name
                ));
    }

    @Test
    void createRequirementExpectState() {
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(id1))
                .when(new EmployeeRequirementCreateCommand(
                        id1,
                        id2,
                        Specialization.CLEANER,
                        name
                ))
                .expectState(a -> {
                    assertEquals(id1, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(1, a.getRequirementMap().size());
                    assertEquals(
                            new EmployeeRequirementEntity(
                                    id2,
                                    Specialization.CLEANER,
                                    name
                            ),
                            a.getRequirementMap().get(id2)
                    );
                });
    }

    @Test
    void createEquipmentExpectEvents() {
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(id1))
                .when(new EquipmentCreateCommand(
                        id1,
                        id2,
                        name
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EquipmentCreatedEvent(
                        id1,
                        id2,
                        name,
                        null
                ));
    }

    @Test
    void createEquipmentExpectState() {
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(id1))
                .when(new EquipmentCreateCommand(
                        id1,
                        id2,
                        name
                ))
                .expectState(a -> {
                    assertEquals(id1, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(1, a.getEquipmentMap().size());
                    assertEquals(
                            new EquipmentEntity(id2, name, null),
                            a.getEquipmentMap().get(id2)
                    );
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    void assignZeroEquipmentExpectEvents() {
        fixture.given(
                        new InventoryCreatedEvent(id1),
                        new EquipmentCreatedEvent(id1, id3, "hammer", null),
                        new EquipmentCreatedEvent(id1, id4, "shovel", null)
                )
                .when(new EquipmentAssignCommand(
                        id1,
                        "CLEANER",
                        id2
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EquipmentAssignedEvent(
                        id2,
                        "CLEANER"
                ));
    }

}
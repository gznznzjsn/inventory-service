package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.common.command.EquipmentAssignCommand;
import com.gznznzjsn.common.event.EquipmentAssignedEvent;
import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.EmployeeRequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import com.gznznzjsn.inventoryservice.core.model.exception.NotEnoughResourcesException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.deepEquals;
import static org.axonframework.test.matchers.Matchers.listWithAllOf;
import static org.axonframework.test.matchers.Matchers.payloadsMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class InventoryAggregateTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    @Test
    void createInventoryExpectEvents() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        fixture.given()
                .when(new InventoryCreateCommand(inventoryId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new InventoryCreatedEvent(inventoryId));
    }

    @Test
    void createInventoryExpectState() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        fixture.given()
                .when(new InventoryCreateCommand(inventoryId))
                .expectState(a -> {
                    assertEquals(inventoryId, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    void createRequirementExpectEvents() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID requirementId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new EmployeeRequirementCreateCommand(
                        inventoryId,
                        requirementId,
                        Specialization.CLEANER,
                        name
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EmployeeRequirementCreatedEvent(
                        inventoryId,
                        requirementId,
                        Specialization.CLEANER,
                        name
                ));
    }

    @Test
    void createRequirementExpectState() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID requirementId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new EmployeeRequirementCreateCommand(
                        inventoryId,
                        requirementId,
                        Specialization.CLEANER,
                        name
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
                                    name
                            ),
                            a.getRequirementMap().get(requirementId)
                    );
                });
    }

    @Test
    void createEquipmentExpectEvents() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID equipmentId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new EquipmentCreateCommand(
                        inventoryId,
                        equipmentId,
                        name
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EquipmentCreatedEvent(
                        inventoryId,
                        equipmentId,
                        name,
                        null
                ));
    }

    @Test
    void createEquipmentExpectState() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID equipmentId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        String name = "FAKE NAME";
        fixture.given(new InventoryCreatedEvent(inventoryId))
                .when(new EquipmentCreateCommand(
                        inventoryId,
                        equipmentId,
                        name
                ))
                .expectState(a -> {
                    assertEquals(inventoryId, a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(1, a.getEquipmentMap().size());
                    assertEquals(
                            new EquipmentEntity(equipmentId, name, null),
                            a.getEquipmentMap().get(equipmentId)
                    );
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    void assignZeroEquipmentWithAvailableExpectEvents() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                null
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                null
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EquipmentAssignedEvent(ownerId, "CLEANER"));
    }

    @Test
    void assignZeroEquipmentWithAvailableExpectState() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                null
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                null
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId
                ))
                .expectState(
                        a -> a.getEquipmentMap()
                                .forEach((k, v) -> assertNull(v.getOwnerId()))
                );
    }

    @Test
    void assignZeroEquipmentWithUnavailableExpectEvents() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        UUID firstEmployeeId =
                UUID.fromString("73ba3fbf-0738-4700-94b2-04ea02cf7114");
        UUID secondEmployeeId =
                UUID.fromString("69f9995e-8d8e-4148-a2fc-a554ce40e5b4");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                firstEmployeeId
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                secondEmployeeId
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId
                ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EquipmentAssignedEvent(ownerId, "CLEANER"));
    }

    @Test
    void assignZeroEquipmentWithUnavailableExpectState() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        UUID firstEmployeeId =
                UUID.fromString("73ba3fbf-0738-4700-94b2-04ea02cf7114");
        UUID secondEmployeeId =
                UUID.fromString("69f9995e-8d8e-4148-a2fc-a554ce40e5b4");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                firstEmployeeId
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                secondEmployeeId
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId
                ))
                .expectState(a -> {
                    assertEquals(
                            firstEmployeeId,
                            a.getEquipmentMap()
                                    .get(firstEquipmentId).getOwnerId()
                    );
                    assertEquals(
                            secondEmployeeId,
                            a.getEquipmentMap()
                                    .get(secondEquipmentId).getOwnerId()
                    );
                });
    }

    @Test
    void assignMultipleEquipmentWithAvailableExpectEvents() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        UUID firstRequirementId =
                UUID.fromString("73ba3fbf-0738-4700-94b2-04ea02cf7114");
        UUID secondRequirementId =
                UUID.fromString("69f9995e-8d8e-4148-a2fc-a554ce40e5b4");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EmployeeRequirementCreatedEvent(
                                inventoryId,
                                firstRequirementId,
                                Specialization.CLEANER,
                                "hammer"
                        ),
                        new EmployeeRequirementCreatedEvent(
                                inventoryId,
                                secondRequirementId,
                                Specialization.CLEANER,
                                "shovel"
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                null
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                null
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId
                ))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(listWithAllOf(
                        deepEquals(new EquipmentOwnerAddedEvent(
                                firstEquipmentId,
                                ownerId
                        )),
                        deepEquals(new EquipmentOwnerAddedEvent(
                                secondEquipmentId,
                                ownerId
                        )),
                        deepEquals(new EquipmentAssignedEvent(
                                ownerId,
                                "CLEANER"
                        ))

                )));
    }

    @Test
    void assignMultipleEquipmentWithAvailableExpectState() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        UUID firstRequirementId =
                UUID.fromString("73ba3fbf-0738-4700-94b2-04ea02cf7114");
        UUID secondRequirementId =
                UUID.fromString("69f9995e-8d8e-4148-a2fc-a554ce40e5b4");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EmployeeRequirementCreatedEvent(
                                inventoryId,
                                firstRequirementId,
                                Specialization.CLEANER,
                                "hammer"
                        ),
                        new EmployeeRequirementCreatedEvent(
                                inventoryId,
                                secondRequirementId,
                                Specialization.CLEANER,
                                "shovel"
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                null
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                null
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId)
                )
                .expectState(a -> {
                    assertEquals(
                            ownerId,
                            a.getEquipmentMap()
                                    .get(firstEquipmentId).getOwnerId()
                    );
                    assertEquals(
                            ownerId,
                            a.getEquipmentMap()
                                    .get(secondEquipmentId).getOwnerId()
                    );
                });
    }

    @Test
    void assignMultipleEquipmentWithUnavailableExpectException() {
        UUID inventoryId =
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID ownerId =
                UUID.fromString("8f89c40e-fe0d-11ed-be56-0242ac120002");
        UUID firstEquipmentId =
                UUID.fromString("289ac0b7-d5d9-4f80-908d-efa0571a8179");
        UUID secondEquipmentId =
                UUID.fromString("1252b098-7784-4598-bfb3-1d50bdb3801a");
        UUID firstRequirementId =
                UUID.fromString("73ba3fbf-0738-4700-94b2-04ea02cf7114");
        UUID secondRequirementId =
                UUID.fromString("69f9995e-8d8e-4148-a2fc-a554ce40e5b4");
        UUID firstEmployeeId =
                UUID.fromString("fa97215b-b234-4724-abd7-c8e7a700e67b");
        fixture.given(
                        new InventoryCreatedEvent(inventoryId),
                        new EmployeeRequirementCreatedEvent(
                                inventoryId,
                                firstRequirementId,
                                Specialization.CLEANER,
                                "hammer"
                        ),
                        new EmployeeRequirementCreatedEvent(
                                inventoryId,
                                secondRequirementId,
                                Specialization.CLEANER,
                                "shovel"
                        ),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                firstEquipmentId,
                                "hammer",
                                firstEmployeeId),
                        new EquipmentCreatedEvent(
                                inventoryId,
                                secondEquipmentId,
                                "shovel",
                                null
                        )
                )
                .when(new EquipmentAssignCommand(
                        inventoryId,
                        "CLEANER",
                        ownerId
                ))
                .expectException(NotEnoughResourcesException.class)
                .expectNoEvents();
    }

}

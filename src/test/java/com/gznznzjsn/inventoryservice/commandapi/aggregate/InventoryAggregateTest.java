package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.matchers.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class InventoryAggregateTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    @Test
    void handleInventoryCreateCommand() {
        fixture.given()
                .when(new InventoryCreateCommand())
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(exactSequenceOf(
                        messageWithPayload(matches(payload ->
                                payload.getClass().equals(
                                        InventoryCreatedEvent.class
                                )
                                && ((InventoryCreatedEvent) payload)
                                           .getInventoryId() != null
                        )),
                        andNoMore()
                ))
                .expectState(a -> {
                    assertNotNull(a.getInventoryId());
                    assertNotNull(a.getEquipmentMap());
                    assertEquals(0, a.getEquipmentMap().size());
                    assertNotNull(a.getRequirementMap());
                    assertEquals(0, a.getRequirementMap().size());
                });
    }

    @Test
    void onInventoryCreatedEvent() {
        fixture.given()
                .when(new InventoryCreatedEvent(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
                ))
                .expectSuccessfulHandlerExecution();
//                .expectEventsMatching(exactSequenceOf(
//                        messageWithPayload(matches(payload ->
//                                payload.getClass().equals(
//                                        InventoryCreatedEvent.class
//                                )
//                                && ((InventoryCreatedEvent) payload)
//                                           .getInventoryId() != null
//                        )),
//                        andNoMore()
//                ));

    }
}
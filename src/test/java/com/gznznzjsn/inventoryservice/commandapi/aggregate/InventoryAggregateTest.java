package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.matchers.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class InventoryAggregateTest {

    private final FixtureConfiguration<InventoryAggregate> fixture =
            new AggregateTestFixture<>(InventoryAggregate.class);

    @org.junit.jupiter.api.Test
    void createInventory() {
        fixture
                .given()
                .when(new InventoryCreateCommand())
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(Matchers.payloadsMatching(
                        Matchers.exactSequenceOf(
                                Matchers.predicate(e ->
                                        e.getClass().equals(InventoryCreatedEvent.class)
                                        && Objects.nonNull(
                                                ((InventoryCreatedEvent) e).getInventoryId()
                                        )
                                )
                        )
                ));
    }
}
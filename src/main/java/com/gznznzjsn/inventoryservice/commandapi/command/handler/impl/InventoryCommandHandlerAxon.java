package com.gznznzjsn.inventoryservice.commandapi.command.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.handler.InventoryCommandHandler;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryCommandHandlerAxon implements InventoryCommandHandler {

    private final Repository<InventoryAggregate> repository;

    @Override
    @CommandHandler(routingKey = "inventoryId")
    public void handle(final InventoryCreateCommand cmd) throws Exception {
        repository.loadOrCreate(
                        cmd.getInventoryId().toString(),
                        InventoryAggregate::new
                )
                .execute(a -> AggregateLifecycle.apply(
                        new InventoryCreatedEvent(cmd.getInventoryId())
                ));
    }

    @Override
    @CommandHandler(routingKey = "inventoryId")
    public void handle(final InventoryDeleteCommand cmd) {
        repository.load(cmd.getInventoryId().toString())
                .execute(a -> AggregateLifecycle.apply(
                        new InventoryDeletedEvent(cmd.getInventoryId())
                ));
    }

}

package com.gznznzjsn.inventoryservice.commandapi.command.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate;
import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.handler.RequirementCommandHandler;
import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequirementCommandHandlerAxon implements RequirementCommandHandler {

    private final Repository<InventoryAggregate> repository;

    @Override
    @CommandHandler(routingKey = "inventoryId")
    public void handle(final RequirementCreateCommand cmd) {
        repository.load(cmd.getInventoryId().toString())
                .execute(a -> AggregateLifecycle.apply(
                        new RequirementCreatedEvent(
                                a.getInventoryId(),
                                cmd.getRequirementId(),
                                cmd.getSpecialization(),
                                cmd.getName()
                        )
                ));
    }

}

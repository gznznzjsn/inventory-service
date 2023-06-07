package com.gznznzjsn.inventoryservice.commandapi.command.handler.impl;

import com.gznznzjsn.common.command.EquipmentAssignCommand;
import com.gznznzjsn.common.event.EquipmentAssignedEvent;
import com.gznznzjsn.inventoryservice.commandapi.aggregate.EquipmentEntity;
import com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.handler.EquipmentCommandHandler;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.core.model.exception.NotEnoughResourcesException;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class EquipmentCommandHandlerAxon implements EquipmentCommandHandler {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final Repository<InventoryAggregate> repository;

    @Override
    @CommandHandler(routingKey = "inventoryId")
    public void handle(final EquipmentCreateCommand cmd) {
        repository.load(cmd.getInventoryId().toString())
                .execute(a -> AggregateLifecycle.apply(
                        new EquipmentCreatedEvent(
                                a.getInventoryId(),
                                cmd.getEquipmentId(),
                                cmd.getName(),
                                cmd.getManufacturer(),
                                cmd.getDescription(),
                                null
                        )
                ));
    }

    @Override
    @CommandHandler(routingKey = "inventoryId")
    public void handle(final EquipmentAssignCommand cmd) {
        repository.load(cmd.getInventoryId().toString()).execute(a -> {
            List<EquipmentOwnerAddedEvent> events = new ArrayList<>();
            a.getRequirementMap().values().stream()
                    .filter(
                            r -> r.getSpecialization().toString()
                                    .equals(cmd.getSpecialization())
                    )
                    .forEach(r -> {
                        EquipmentEntity equipment = a.getEquipmentMap()
                                .values().stream()
                                .filter(e -> e.getName().equals(r.getName())
                                             && e.getOwnerId() == null)
                                .findAny().orElseThrow(() ->
                                        new NotEnoughResourcesException(
                                                "Not enough equipment for "
                                                + cmd.getSpecialization()
                                        )
                                );
                        events.add(new EquipmentOwnerAddedEvent(
                                equipment.getEquipmentId(),
                                cmd.getOwnerId()
                        ));
                    });
            events.forEach(AggregateLifecycle::apply);
            AggregateLifecycle.apply(new EquipmentAssignedEvent(
                    cmd.getOwnerId(),
                    cmd.getSpecialization()
            ));
        });

    }

}

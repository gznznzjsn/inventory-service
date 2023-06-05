package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.commandapi.event.*;
import com.gznznzjsn.inventoryservice.commandapi.event.sourcing.EquipmentSourcingHandler;
import com.gznznzjsn.inventoryservice.commandapi.event.sourcing.InventorySourcingHandler;
import com.gznznzjsn.inventoryservice.commandapi.event.sourcing.RequirementSourcingHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
@Data
@NoArgsConstructor
public class InventoryAggregate implements InventorySourcingHandler,
        RequirementSourcingHandler, EquipmentSourcingHandler {

    @AggregateIdentifier
    private UUID inventoryId;

    @AggregateMember
    private Map<UUID, RequirementEntity> requirementMap;

    @AggregateMember
    private Map<UUID, EquipmentEntity> equipmentMap;

    @Override
    @EventSourcingHandler
    public void on(final InventoryCreatedEvent event) {
        this.inventoryId = event.getInventoryId();
        this.requirementMap = new HashMap<>();
        this.equipmentMap = new HashMap<>();
    }

    @Override
    @EventSourcingHandler
    public void on(final InventoryDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }

    @Override
    @EventSourcingHandler
    public void on(final RequirementCreatedEvent event) {
        this.requirementMap.put(
                event.getRequirementId(),
                new RequirementEntity(
                        event.getRequirementId(),
                        event.getSpecialization(),
                        event.getName()
                )
        );
    }

    @Override
    @EventSourcingHandler
    public void on(final EquipmentCreatedEvent event) {
        this.equipmentMap.put(
                event.getEquipmentId(),
                new EquipmentEntity(
                        event.getEquipmentId(),
                        event.getName(),
                        event.getManufacturer(),
                        event.getDescription(),
                        event.getOwnerId()
                )
        );
    }

    @Override
    @EventSourcingHandler
    public void on(final EquipmentOwnerAddedEvent event) {
        equipmentMap.get(event.getEquipmentId())
                .setOwnerId(event.getOwnerId());
    }

}

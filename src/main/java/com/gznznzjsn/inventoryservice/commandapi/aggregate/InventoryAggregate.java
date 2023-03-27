package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.EmployeeRequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.saga.command.EquipmentAssignCommand;
import com.gznznzjsn.saga.event.EquipmentAssignedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class InventoryAggregate {

    @AggregateIdentifier
    private UUID inventoryId;

    @AggregateMember
    private Map<UUID, EmployeeRequirementEntity> employeeRequirementMap;

    @AggregateMember
    private Map<UUID, EquipmentEntity> equipmentMap;

    @CommandHandler
    public InventoryAggregate(InventoryCreateCommand command) {
        AggregateLifecycle.apply(new InventoryCreatedEvent(
                UUID.randomUUID()
        ));
    }

    @EventSourcingHandler
    public void on(InventoryCreatedEvent event) {
        this.inventoryId = event.getInventoryId();
        this.employeeRequirementMap = new HashMap<>();
        this.equipmentMap = new HashMap<>();
    }

    @CommandHandler
    public void handle(EmployeeRequirementCreateCommand command) {
        AggregateLifecycle.apply(new EmployeeRequirementCreatedEvent(
                this.inventoryId,
                UUID.randomUUID(),
                command.getSpecialization(),
                command.getEquipmentName()
        ));
    }

    @EventSourcingHandler
    public void on(EmployeeRequirementCreatedEvent event) {
        this.employeeRequirementMap.put(
                event.getEmployeeRequirementId(),
                new EmployeeRequirementEntity(
                        event.getEmployeeRequirementId(),
                        event.getSpecialization(),
                        event.getEquipmentName()
                )
        );
    }

    @CommandHandler
    public void handle(EquipmentCreateCommand command) {
        AggregateLifecycle.apply(new EquipmentCreatedEvent(
                this.inventoryId,
                UUID.randomUUID(),
                command.getEquipmentName(),
                null
        ));
    }

    @EventSourcingHandler
    public void on(EquipmentCreatedEvent event) {
        this.equipmentMap.put(
                event.getEquipmentId(),
                new EquipmentEntity(
                        event.getEquipmentId(),
                        event.getEquipmentName(),
                        event.getOwnerId()
                )
        );
    }

    @CommandHandler
    public void handle(EquipmentAssignCommand command) {
        AggregateLifecycle.apply(new EquipmentAssignedEvent(
                command.getOwnerId()
        ));
    }

    @EventSourcingHandler
    public void on(EquipmentAssignedEvent event) {
//        this.equipmentMap.put(
//                event.getEquipmentId(),
//                new EquipmentEntity(
//                        event.getEquipmentId(),
//                        event.getEquipmentName(),
//                        event.getOwnerId()
//                )
//        );
    }


}

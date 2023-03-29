package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.EmployeeRequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import com.gznznzjsn.inventoryservice.core.model.exception.NotEnoughResourcesException;
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
import java.util.List;
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

    @CommandHandler
    public void handle(EmployeeRequirementCreateCommand command) {
        AggregateLifecycle.apply(new EmployeeRequirementCreatedEvent(
                this.inventoryId,
                UUID.randomUUID(),
                command.getSpecialization(),
                command.getEquipmentName()
        ));
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

    @CommandHandler
    public void handle(EquipmentAssignCommand command) {
        checkEquipmentAvailability(Specialization.valueOf(command.getSpecialization()));
        AggregateLifecycle.apply(new EquipmentAssignedEvent(
                command.getOwnerId(),
                command.getSpecialization()
        ));
    }

    @EventSourcingHandler
    public void on(InventoryCreatedEvent event) {
        this.inventoryId = event.getInventoryId();
        this.employeeRequirementMap = new HashMap<>();
        this.equipmentMap = new HashMap<>();
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

    @EventSourcingHandler
    public void on(EquipmentAssignedEvent event) {
        List<EmployeeRequirementEntity> requirements = employeeRequirementMap
                .values().stream()
                .filter(r -> r.getSpecialization().toString()
                        .equals(event.getSpecialization())).toList();
        List<EquipmentEntity> equipment = equipmentMap.values()
                .stream().toList();
        for (int i = 0, j = 0; i < requirements.size() && j < equipment.size(); i++) {
            while (!requirements.get(i).getEquipmentName()
                    .equals(equipment.get(j).getEquipmentName())
                   || equipment.get(j).getOwnerId() != null) {
                j++;
            }
            UUID equipmentId = equipment.get(j).getEquipmentId();
            equipmentMap.get(equipmentId).setOwnerId(event.getEmployeeId());
            AggregateLifecycle.apply(new EquipmentOwnerAddedEvent(
                    equipmentId,
                    event.getEmployeeId()
            ));
        }

    }

    private void checkEquipmentAvailability(Specialization specialization) {
        employeeRequirementMap.values().stream()
                .filter(r -> r.getSpecialization()
                        .equals(specialization))
                .forEach(r -> {
                    if (equipmentMap.values().stream()
                            .noneMatch(
                                    e -> e.getEquipmentName().equals(r.getEquipmentName())
                                         && e.getOwnerId() == null)
                    ) {
                        throw new NotEnoughResourcesException("Not enough equipment for " + specialization);
                    }
                });
    }

}

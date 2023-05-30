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
import com.gznznzjsn.inventoryservice.core.model.exception.NotEnoughResourcesException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Aggregate
@Data
@NoArgsConstructor
public class InventoryAggregate {

    @AggregateIdentifier
    private UUID inventoryId;

    @AggregateMember
    private Map<UUID, EmployeeRequirementEntity> requirementMap;

    @AggregateMember
    private Map<UUID, EquipmentEntity> equipmentMap;

    /**
     * Handles {@link InventoryCreateCommand} and applies
     * {@link InventoryCreatedEvent} to create new aggregate.
     *
     * @param cmd indicates, that
     *            {@link com.gznznzjsn.inventoryservice.core.model.Inventory}
     *            should be created
     */
    @CommandHandler
    public InventoryAggregate(final InventoryCreateCommand cmd) {
        AggregateLifecycle.apply(new InventoryCreatedEvent(
                cmd.getInventoryId()
        ));
    }

    /**
     * Applies {@link EmployeeRequirementCreatedEvent}, which will create
     * {@link com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement}
     * in current aggregate.
     *
     * @param cmd provides id of target aggregate and values to initialize
     *            {@link
     *            com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement}
     */
    @CommandHandler
    public void handle(final EmployeeRequirementCreateCommand cmd) {
        AggregateLifecycle.apply(new EmployeeRequirementCreatedEvent(
                this.inventoryId,
                cmd.getRequirementId(),
                cmd.getSpecialization(),
                cmd.getName()
        ));
    }

    /**
     * Applies {@link EquipmentCreatedEvent}, which will create
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     * in current aggregate.
     *
     * @param cmd provides id of target aggregate and
     *            values to initialize
     *            {@link
     *            com.gznznzjsn.inventoryservice.core.model.Equipment}
     */
    @CommandHandler
    public void handle(final EquipmentCreateCommand cmd) {
        AggregateLifecycle.apply(new EquipmentCreatedEvent(
                this.inventoryId,
                cmd.getEquipmentId(),
                cmd.getName(),
                cmd.getManufacturer(),
                cmd.getDescription(),
                null
        ));
    }

    /**
     * Checks availability of
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}, finds
     * appropriate {@link EquipmentEntity} with null owner id, which has
     * requested
     * {@link com.gznznzjsn.inventoryservice.core.model.Specialization}
     * and applies {@link EquipmentOwnerAddedEvent},
     * which will assign
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     * to owner in current aggregate.
     *
     * @param cmd provides id of target aggregate and
     *            values to set owner and its {@link
     *            com.gznznzjsn.inventoryservice.core.model.Specialization}
     * @throws NotEnoughResourcesException if requested
     *                                     equipment is not
     *                                     available
     */
    @CommandHandler
    public void handle(final EquipmentAssignCommand cmd) {
        List<EquipmentOwnerAddedEvent> events = new ArrayList<>();
        requirementMap.values().stream()
                .filter(
                        r -> r.getSpecialization().toString()
                                .equals(cmd.getSpecialization())
                )
                .forEach(r -> {
                    EquipmentEntity equipment = equipmentMap.values().stream()
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
    }

    /**
     * Handles {@link InventoryCreatedEvent} extracts id of aggregate and
     * all fields of new
     * {@link InventoryAggregate}.
     *
     * @param event indicates, that
     *              {@link InventoryAggregate} should be
     *              created and provides id for it
     */
    @EventSourcingHandler
    public void on(final InventoryCreatedEvent event) {
        this.inventoryId = event.getInventoryId();
        this.requirementMap = new HashMap<>();
        this.equipmentMap = new HashMap<>();
    }

    /**
     * Handles {@link EmployeeRequirementCreatedEvent} extracts all fields from
     * it, initializes all fields of {@link EmployeeRequirementEntity} and
     * adds it to current aggregate.
     *
     * @param event provides fields for new instance of
     *              {@link EmployeeRequirementEntity}
     */
    @EventSourcingHandler
    public void on(final EmployeeRequirementCreatedEvent event) {
        this.requirementMap.put(
                event.getRequirementId(),
                new EmployeeRequirementEntity(
                        event.getRequirementId(),
                        event.getSpecialization(),
                        event.getName()
                )
        );
    }

    /**
     * Handles {@link EquipmentCreatedEvent} extracts all fields from
     * it, initializes all fields of {@link EquipmentEntity} and
     * adds it to current aggregate.
     *
     * @param event provides fields for new instance of
     *              {@link EquipmentEntity}
     */
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

    /**
     * Handles {@link EquipmentOwnerAddedEvent} and adds owner to extracted
     * from event {@link EquipmentEntity}.
     *
     * @param event provides parameters for successful assignment of {@link
     *              EquipmentEntity}
     */
    @EventSourcingHandler
    public void on(final EquipmentOwnerAddedEvent event) {
        equipmentMap.get(event.getEquipmentId())
                .setOwnerId(event.getOwnerId());
    }

}

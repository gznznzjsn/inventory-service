package com.gznznzjsn.common.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentAssignCommand {

    /**
     * Identity of target {@link
    com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate}.
     */
    @TargetAggregateIdentifier
    private UUID inventoryId;

    /**
     * {@link String} value of
     * {@link com.gznznzjsn.inventoryservice.core.model.Specialization}, which
     * has a possible owner of
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}.
     */
    private String specialization;

    /**
     * Identity of {@link com.gznznzjsn.inventoryservice.core.model.Employee},
     * to whom {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     * should be assigned.
     */
    private UUID ownerId;

}

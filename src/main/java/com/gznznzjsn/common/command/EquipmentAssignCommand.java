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

    @TargetAggregateIdentifier
    private UUID inventoryId;
    private String specialization;
    private UUID ownerId;

}

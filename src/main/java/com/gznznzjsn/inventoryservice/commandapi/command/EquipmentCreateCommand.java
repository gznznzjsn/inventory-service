package com.gznznzjsn.inventoryservice.commandapi.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class EquipmentCreateCommand {

    @TargetAggregateIdentifier
    private final UUID inventoryId;
    private final String equipmentName;

}

package com.gznznzjsn.inventoryservice.commandapi.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentCreateCommand {

    @TargetAggregateIdentifier
    private UUID inventoryId;
    private String name;
    private String manufacturer;
    private String description;

}

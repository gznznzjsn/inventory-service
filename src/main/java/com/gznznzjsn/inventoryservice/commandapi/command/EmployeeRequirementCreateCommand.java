package com.gznznzjsn.inventoryservice.commandapi.command;

import com.gznznzjsn.inventoryservice.core.model.Specialization;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class EmployeeRequirementCreateCommand {

    @TargetAggregateIdentifier
    private final UUID inventoryId;
    private final Specialization specialization;
    private final String equipmentName;

}

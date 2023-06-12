package com.gznznzjsn.inventoryservice.commandapi.command;

import com.gznznzjsn.inventoryservice.core.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementCreateCommand {

    @TargetAggregateIdentifier
    private UUID inventoryId;
    private UUID requirementId;
    private Specialization specialization;
    private String name;

}

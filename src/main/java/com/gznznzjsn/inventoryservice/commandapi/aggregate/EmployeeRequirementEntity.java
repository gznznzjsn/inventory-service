package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import com.gznznzjsn.inventoryservice.core.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EmployeeRequirementEntity {

    @EntityId
    private UUID employeeRequirementId;
    private Specialization specialization;
    private String equipmentName;

}

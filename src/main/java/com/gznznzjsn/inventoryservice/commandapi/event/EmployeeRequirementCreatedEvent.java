package com.gznznzjsn.inventoryservice.commandapi.event;

import com.gznznzjsn.inventoryservice.core.model.Specialization;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeRequirementCreatedEvent {

    private final UUID inventoryId;
    private final UUID employeeRequirementId;
    private final Specialization specialization;
    private final String equipmentName;

}

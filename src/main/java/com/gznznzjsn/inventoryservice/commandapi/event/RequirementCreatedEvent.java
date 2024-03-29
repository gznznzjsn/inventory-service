package com.gznznzjsn.inventoryservice.commandapi.event;

import com.gznznzjsn.inventoryservice.core.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementCreatedEvent {

    private UUID inventoryId;
    private UUID requirementId;
    private Specialization specialization;
    private String name;

}

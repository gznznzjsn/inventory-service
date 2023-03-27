package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EquipmentEntity {

    @EntityId
    private UUID equipmentId;
    private String equipmentName;
    private UUID ownerId;

}

package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import lombok.*;
import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EquipmentEntity {

    @EntityId
    private UUID equipmentId;
    private String name;
    private UUID ownerId;

}

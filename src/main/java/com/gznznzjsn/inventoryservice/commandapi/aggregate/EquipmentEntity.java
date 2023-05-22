package com.gznznzjsn.inventoryservice.commandapi.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EquipmentEntity {

    @EntityId
    private UUID equipmentId;
    private String name;
    private String manufacturer;
    private String description;
    private UUID ownerId;

}

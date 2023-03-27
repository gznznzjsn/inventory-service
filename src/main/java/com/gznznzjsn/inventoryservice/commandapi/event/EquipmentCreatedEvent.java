package com.gznznzjsn.inventoryservice.commandapi.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EquipmentCreatedEvent {

    private final UUID inventoryId;
    private final UUID equipmentId;
    private final String equipmentName;
    private final UUID ownerId;

}

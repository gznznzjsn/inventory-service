package com.gznznzjsn.inventoryservice.commandapi.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EquipmentOwnerAddedEvent {

    private final UUID equipmentId;
    private final UUID ownerId;

}
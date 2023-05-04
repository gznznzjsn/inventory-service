package com.gznznzjsn.inventoryservice.commandapi.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentCreatedEvent {

    private UUID inventoryId;
    private UUID equipmentId;
    private String name;
    private UUID ownerId;

}

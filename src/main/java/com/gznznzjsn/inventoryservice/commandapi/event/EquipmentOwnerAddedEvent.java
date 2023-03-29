package com.gznznzjsn.inventoryservice.commandapi.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentOwnerAddedEvent {

    private UUID equipmentId;
    private UUID ownerId;

}
package com.gznznzjsn.inventoryservice.commandapi.event;

import lombok.Data;

import java.util.UUID;

@Data
public class InventoryCreatedEvent {

    private final UUID inventoryId;

}

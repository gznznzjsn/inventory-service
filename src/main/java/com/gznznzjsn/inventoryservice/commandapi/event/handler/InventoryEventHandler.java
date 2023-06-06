package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;
import com.gznznzjsn.inventoryservice.core.model.Inventory;

public interface InventoryEventHandler {

    /**
     * Handles {@link InventoryCreatedEvent} and creates {@link Inventory}.
     *
     * @param event provides fields of {@link Inventory}, which need to be
     *              persisted
     */
    void on(InventoryCreatedEvent event);

    void on(InventoryDeletedEvent event);

}

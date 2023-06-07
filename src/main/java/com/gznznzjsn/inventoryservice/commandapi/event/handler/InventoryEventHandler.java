package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;

public interface InventoryEventHandler {

    /**
     * Handles {@link InventoryCreatedEvent} and creates {@link
     * com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @param event provides fields of {@link
     *              com.gznznzjsn.inventoryservice.core.model.Inventory},
     *              which need to be persisted
     */
    void on(InventoryCreatedEvent event);

    /**
     * Handles {@link InventoryDeletedEvent} and deletes {@link
     * com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @param event provides id of {@link
     *              com.gznznzjsn.inventoryservice.core.model.Inventory} to be
     *              deleted
     */
    void on(InventoryDeletedEvent event);

}

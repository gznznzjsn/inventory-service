package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;

import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;

public interface InventorySourcingHandler {

    /**
     * Handles {@link InventoryCreatedEvent} extracts id of aggregate and
     * all fields of new {@link
     * com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate}.
     *
     * @param event indicates, that aggregate should be
     *              created and provides id for it
     */
    void on(InventoryCreatedEvent event);

    /**
     * Handles {@link InventoryDeletedEvent} extracts id of aggregate.
     *
     * @param event indicates, that aggregate should be deleted
     */
    void on(InventoryDeletedEvent event);

}

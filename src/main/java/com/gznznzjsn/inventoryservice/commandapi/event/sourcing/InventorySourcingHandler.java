package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;

import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;

public interface InventorySourcingHandler {

    void on(InventoryDeletedEvent event);

    /**
     * Handles {@link InventoryCreatedEvent} extracts id of aggregate and
     * all fields of new {@link
     * com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate}.
     *
     * @param event indicates, that aggregate should be
     *              created and provides id for it
     */
    void on(InventoryCreatedEvent event);

}

package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;

import com.gznznzjsn.inventoryservice.commandapi.aggregate.InventoryAggregate;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;

public interface InventorySourcingHandler {

    void on(final InventoryDeletedEvent event);

    /**
     * Handles {@link InventoryCreatedEvent} extracts id of aggregate and
     * all fields of new
     * {@link InventoryAggregate}.
     *
     * @param event indicates, that
     *              {@link InventoryAggregate} should be
     *              created and provides id for it
     */
    void on(final InventoryCreatedEvent event);

}

package com.gznznzjsn.inventoryservice.commandapi.command.handler;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;

public interface InventoryCommandHandler {

    /**
     * Creates new Inventory Aggregate.
     *
     * @param cmd indicates, that
     *            {@link com.gznznzjsn.inventoryservice.core.model.Inventory}
     *            should be created
     */
    void handle(final InventoryCreateCommand cmd);

    void handle(InventoryDeleteCommand cmd);

}

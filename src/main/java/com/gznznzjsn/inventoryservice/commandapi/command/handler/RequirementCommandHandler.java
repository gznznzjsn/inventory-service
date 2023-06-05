package com.gznznzjsn.inventoryservice.commandapi.command.handler;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.core.model.Requirement;

public interface RequirementCommandHandler {

    /**
     * Creates new {@link Requirement}
     * in {@link com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @param cmd provides id of target aggregate and values to initialize
     *            {@link
     *            Requirement}
     */
    void handle(final RequirementCreateCommand cmd);

}

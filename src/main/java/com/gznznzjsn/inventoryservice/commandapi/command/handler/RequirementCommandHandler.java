package com.gznznzjsn.inventoryservice.commandapi.command.handler;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;

public interface RequirementCommandHandler {

    /**
     * Creates new requirement in inventory.
     *
     * @param cmd provides id of target aggregate and values to initialize
     *            requirement.
     */
    void handle(RequirementCreateCommand cmd);

}

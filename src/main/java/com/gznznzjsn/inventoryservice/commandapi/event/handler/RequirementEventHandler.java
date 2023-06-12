package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;

public interface RequirementEventHandler {

    /**
     * Handles {@link RequirementCreatedEvent}, extracts values, builds
     * {@link com.gznznzjsn.inventoryservice.core.model.Requirement}
     * and saves it to repository.
     *
     * @param event provides values to initialize new
     *              {@link
     *              com.gznznzjsn.inventoryservice.core.model.Requirement}
     */
    void on(RequirementCreatedEvent event);

}

package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Requirement;
import com.gznznzjsn.inventoryservice.core.persistence.repository.RequirementRepository;

public interface RequirementEventHandler {

    /**
     * Handles {@link RequirementCreatedEvent}, extracts values, builds
     * {@link Requirement} and saves it to
     * {@link RequirementRepository}.
     *
     * @param event provides values to initialize new
     *              {@link Requirement}
     */
    void on(RequirementCreatedEvent event);

}

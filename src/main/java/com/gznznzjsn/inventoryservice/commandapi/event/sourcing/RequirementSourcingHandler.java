package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;

import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;

public interface RequirementSourcingHandler {

    /**
     * Handles {@link RequirementCreatedEvent} extracts all fields from
     * it, initializes all fields of {@link
     * com.gznznzjsn.inventoryservice.commandapi.aggregate.RequirementEntity}
     * and adds it to current aggregate.
     *
     * @param event provides fields for new instance of
     *              entity
     */
    void on(RequirementCreatedEvent event);

}

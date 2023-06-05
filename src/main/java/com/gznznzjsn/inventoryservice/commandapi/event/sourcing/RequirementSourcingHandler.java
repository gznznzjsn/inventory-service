package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;

import com.gznznzjsn.inventoryservice.commandapi.aggregate.RequirementEntity;
import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;

public interface RequirementSourcingHandler {

    /**
     * Handles {@link RequirementCreatedEvent} extracts all fields from
     * it, initializes all fields of {@link RequirementEntity} and
     * adds it to current aggregate.
     *
     * @param event provides fields for new instance of
     *              {@link RequirementEntity}
     */
    void on(final RequirementCreatedEvent event);

}

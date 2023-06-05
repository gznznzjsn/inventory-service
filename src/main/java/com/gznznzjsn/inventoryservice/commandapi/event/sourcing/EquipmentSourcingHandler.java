package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;


import com.gznznzjsn.inventoryservice.commandapi.aggregate.EquipmentEntity;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;

public interface EquipmentSourcingHandler {

    /**
     * Handles {@link EquipmentOwnerAddedEvent} and adds owner to extracted
     * from event {@link EquipmentEntity}.
     *
     * @param event provides parameters for successful assignment of {@link
     *              EquipmentEntity}
     */
    void on(final EquipmentOwnerAddedEvent event);

    /**
     * Handles {@link EquipmentCreatedEvent} extracts all fields from
     * it, initializes all fields of {@link EquipmentEntity} and
     * adds it to current aggregate.
     *
     * @param event provides fields for new instance of
     *              {@link EquipmentEntity}
     */
    void on(final EquipmentCreatedEvent event);

}

package com.gznznzjsn.inventoryservice.commandapi.event.sourcing;


import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;

public interface EquipmentSourcingHandler {

    /**
     * Handles {@link EquipmentOwnerAddedEvent} and adds owner to extracted
     * from event {@link
     * com.gznznzjsn.inventoryservice.commandapi.aggregate.EquipmentEntity}.
     *
     * @param event provides parameters for successful assignment of equipment
     */
    void on(EquipmentOwnerAddedEvent event);

    /**
     * Handles {@link EquipmentCreatedEvent} extracts all fields from
     * it, initializes all fields of {@link
     * com.gznznzjsn.inventoryservice.commandapi.aggregate.EquipmentEntity} and
     * adds it to current aggregate.
     *
     * @param event provides fields for new instance of equipment
     */
    void on(EquipmentCreatedEvent event);

}

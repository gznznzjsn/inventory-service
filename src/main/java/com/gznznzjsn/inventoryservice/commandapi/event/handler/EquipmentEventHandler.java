package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;

public interface EquipmentEventHandler {

    /**
     * Handles {@link EquipmentCreatedEvent} extracts all fields from
     * it, initializes all fields of
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment} and
     * saves it to repository.
     *
     * @param event provides fields for new instance of
     *              {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     */
    void on(EquipmentCreatedEvent event);

    /**
     * Handles {@link EquipmentOwnerAddedEvent} extracts
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     * identity, owner identity, finds {@link
     * com.gznznzjsn.inventoryservice.core.model.Equipment} and
     * sets owner.
     *
     * @param event provides
     *              {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     *              and owner identity
     */
    void on(EquipmentOwnerAddedEvent event);

}

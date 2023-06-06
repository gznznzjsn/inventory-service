package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentRepository;

public interface EquipmentEventHandler {

    /**
     * Handles {@link EquipmentCreatedEvent} extracts all fields from
     * it, initializes all fields of
     * {@link Equipment} and
     * saves it to {@link EquipmentRepository}.
     *
     * @param event provides fields for new instance of
     *              {@link Equipment}
     */
    void on(EquipmentCreatedEvent event);

    /**
     * Handles {@link EquipmentOwnerAddedEvent} extracts
     * {@link Equipment} identity, owner identity, finds {@link Equipment} and
     * sets owner.
     *
     * @param event provides {@link Equipment} and owner identity
     */
    void on(EquipmentOwnerAddedEvent event);

}

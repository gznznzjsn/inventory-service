package com.gznznzjsn.inventoryservice.commandapi.command.handler;

import com.gznznzjsn.common.command.EquipmentAssignCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;

public interface EquipmentCommandHandler {

    /**
     * Creates new
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     * in {@link com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @param cmd provides id of target aggregate and
     *            values to initialize
     *            {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     */
    void handle(EquipmentCreateCommand cmd);

    /**
     * Checks availability of
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}, finds
     * appropriate and assigns it to provided owner.
     *
     * @param cmd provides id of target aggregate and
     *            values to set owner and its {@link
     *            com.gznznzjsn.inventoryservice.core.model.Specialization}
     */
    void handle(EquipmentAssignCommand cmd);

}

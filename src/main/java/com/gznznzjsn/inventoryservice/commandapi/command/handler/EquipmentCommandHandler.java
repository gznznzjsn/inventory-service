package com.gznznzjsn.inventoryservice.commandapi.command.handler;

import com.gznznzjsn.common.command.EquipmentAssignCommand;
import com.gznznzjsn.inventoryservice.commandapi.aggregate.EquipmentEntity;
import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.core.model.exception.NotEnoughResourcesException;
import org.axonframework.commandhandling.CommandHandler;

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
     * @throws NotEnoughResourcesException if requested
     *                                     equipment is not
     *                                     available
     */
    void handle(EquipmentAssignCommand cmd);

}

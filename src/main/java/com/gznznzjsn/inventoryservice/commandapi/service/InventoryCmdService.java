package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface InventoryCmdService {

    /**
     * @param command indicates, that
     * {@link com.gznznzjsn.inventoryservice.core.model.Inventory} should be
     *                created
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    Mono<UUID> create(InventoryCreateCommand command);

}

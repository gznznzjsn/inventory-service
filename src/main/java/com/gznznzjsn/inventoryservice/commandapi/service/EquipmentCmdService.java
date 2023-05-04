package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EquipmentCmdService {

    /**
     * @param command provides values to initialize new
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}.
     *
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    Mono<UUID> create(EquipmentCreateCommand command);

}

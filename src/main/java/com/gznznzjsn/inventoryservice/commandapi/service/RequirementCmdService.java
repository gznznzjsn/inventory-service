package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RequirementCmdService {

    /**
     * @param command provides values to initialize new
     * {@link com.gznznzjsn.inventoryservice.core.model.Requirement}.
     *
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    Mono<UUID> create(RequirementCreateCommand command);

}

package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeeRequirementCmdService {

    /**
     * @param command provides values to initialize new
     * {@link com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement}.
     *
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    Mono<UUID> create(EmployeeRequirementCreateCommand command);

}

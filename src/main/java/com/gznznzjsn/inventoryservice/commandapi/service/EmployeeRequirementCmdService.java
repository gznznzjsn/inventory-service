package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeeRequirementCmdService {

    Mono<UUID> create(EmployeeRequirementCreateCommand command);

}

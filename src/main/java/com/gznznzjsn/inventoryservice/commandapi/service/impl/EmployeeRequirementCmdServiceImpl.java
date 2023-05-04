package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EmployeeRequirementCmdService;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeRequirementCmdServiceImpl
        implements EmployeeRequirementCmdService {

    private final ReactorCommandGateway gateway;

    @Override
    public Mono<UUID> create(final EmployeeRequirementCreateCommand command) {
        return gateway.send(command);
    }

}

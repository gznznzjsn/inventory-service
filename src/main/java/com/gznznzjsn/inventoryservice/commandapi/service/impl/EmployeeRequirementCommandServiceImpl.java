package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EmployeeRequirementCommandService;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeRequirementCommandServiceImpl implements EmployeeRequirementCommandService {

    private final ReactorCommandGateway commandGateway;

    @Override
    public Mono<UUID> create(EmployeeRequirementCreateCommand command) {
        return commandGateway.send(command);
    }

}

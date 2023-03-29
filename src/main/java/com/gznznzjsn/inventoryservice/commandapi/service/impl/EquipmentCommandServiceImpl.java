package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCommandService;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentCommandServiceImpl implements EquipmentCommandService {

    private final ReactorCommandGateway commandGateway;

    @Override
    public Mono<UUID> create(EquipmentCreateCommand command) {
        return commandGateway.send(command);
    }

}

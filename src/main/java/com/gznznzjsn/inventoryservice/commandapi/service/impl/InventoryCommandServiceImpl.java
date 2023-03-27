package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.InventoryCommandService;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryCommandServiceImpl implements InventoryCommandService {

    private final ReactorCommandGateway commandGateway;

    @Override
    public Mono<UUID> create(InventoryCreateCommand command) {
        return commandGateway.send(command);
    }

}

package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.InventoryCmdService;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryCmdServiceImpl implements InventoryCmdService {

    private final ReactorCommandGateway gateway;

    @Override
    public Mono<UUID> create(final InventoryCreateCommand cmd) {
        return Mono.just(cmd)
                .doOnNext(c -> c.setInventoryId(UUID.randomUUID()))
                .flatMap(gateway::send);
    }

    @Override
    public Mono<UUID> delete(final InventoryDeleteCommand cmd) {
        return gateway.send(cmd);
    }

}

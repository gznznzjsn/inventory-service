package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface InventoryCommandService {

    Mono<UUID> create(InventoryCreateCommand command);

}

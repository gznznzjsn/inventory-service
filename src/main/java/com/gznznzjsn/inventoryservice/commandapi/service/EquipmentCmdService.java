package com.gznznzjsn.inventoryservice.commandapi.service;

import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EquipmentCmdService {

    Mono<UUID> create(EquipmentCreateCommand command);

}

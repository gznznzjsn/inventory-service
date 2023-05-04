package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.InventoryCmdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories")
public class InventoryCommandController {

    private final InventoryCmdService inventoryService;

    /** With the help of {@link InventoryCmdService} creates
     * {@link com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    @PostMapping
    public Mono<UUID> create() {
        return inventoryService.create(new InventoryCreateCommand());
    }

}

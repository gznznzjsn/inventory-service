package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.InventoryCmdService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class InventoryCommandGraphqlController {

    private final InventoryCmdService inventoryService;

    /**
     * Endpoint for GraphQL.
     * With the help of {@link InventoryCmdService} creates
     * {@link com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    @MutationMapping(name = "createInventory")
    public Mono<UUID> create() {
        return inventoryService.create(
                new InventoryCreateCommand(null)
        );
    }

    /**
     * Endpoint for GraphQL.
     * With the help of {@link InventoryCmdService} deletes
     * {@link com.gznznzjsn.inventoryservice.core.model.Inventory}.
     *
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    @MutationMapping(name = "deleteInventory")
    public Mono<UUID> delete(
            final @Argument UUID id
    ) {
        return inventoryService.delete(new InventoryDeleteCommand(id));
    }

}

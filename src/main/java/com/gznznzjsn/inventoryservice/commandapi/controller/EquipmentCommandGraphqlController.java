package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCmdService;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EquipmentCommandGraphqlController {

    private final EquipmentCmdService equipmentService;

    /**
     * Endpoint for GraphQL.
     * With the help of {@link EquipmentCmdService} creates
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}.
     *
     * @param dto         provides values to initialize
     *                    {@link
     *                    com.gznznzjsn.inventoryservice.core.model.Equipment}
     *                    fields.
     * @param inventoryId id of target aggregate, which will store created
     *                    {@link
     *                    com.gznznzjsn.inventoryservice.core.model.Equipment}
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    @MutationMapping(name = "createEquipment")
    public Mono<UUID> create(
            final @Arguments EquipmentDto dto,
            final @Argument UUID inventoryId
    ) {
        return equipmentService.create(
                new EquipmentCreateCommand(
                        inventoryId,
                        null,
                        dto.name(),
                        dto.manufacturer(),
                        dto.description()
                )
        );
    }

}

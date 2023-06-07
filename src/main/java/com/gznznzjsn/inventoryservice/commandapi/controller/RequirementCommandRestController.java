package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.RequirementCmdService;
import com.gznznzjsn.inventoryservice.core.web.dto.RequirementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories/{inventoryId}/requirements")
public class RequirementCommandRestController {

    private final RequirementCmdService requirementService;

    /**
     * With the help of {@link RequirementCmdService} creates
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}.
     *
     * @param dto         provides values to initialize
     *                    entity  fields
     * @param inventoryId id of target aggregate, which will store created
     *                    entity
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    @PostMapping
    public Mono<UUID> create(
            final @RequestBody RequirementDto dto,
            final @PathVariable UUID inventoryId
    ) {
        return requirementService.create(
                new RequirementCreateCommand(
                        inventoryId,
                        null,
                        dto.specialization(),
                        dto.name()
                )
        );
    }

}

package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCmdService;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories/{inventoryId}/equipment")
public class EquipmentCommandRestController {

    private final EquipmentCmdService equipmentService;

    /**
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UUID> create(
            final @RequestBody EquipmentDto dto,
            final @PathVariable UUID inventoryId
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

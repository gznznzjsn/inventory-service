package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCmdService;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories/{inventoryId}/equipment")
public class EquipmentCommandController {

    private final EquipmentCmdService equipmentCmdService;

    @PostMapping
    public Mono<UUID> create(
            final @RequestBody EquipmentDto equipmentDto,
            final @PathVariable UUID inventoryId
    ) {
        return equipmentCmdService.create(
                new EquipmentCreateCommand(
                        inventoryId,
                        equipmentDto.equipmentName()
                )
        );
    }

}

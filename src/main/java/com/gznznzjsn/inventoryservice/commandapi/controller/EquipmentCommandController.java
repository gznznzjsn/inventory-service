package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCommandService;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories/{inventoryId}/equipment")
public class EquipmentCommandController {

    private final EquipmentCommandService equipmentCommandService;

    @PostMapping
    public Mono<UUID> create(@RequestBody EquipmentDto equipmentDto, @PathVariable UUID inventoryId) {
        return equipmentCommandService.create(new EquipmentCreateCommand(inventoryId, equipmentDto.equipmentName()));
    }

}

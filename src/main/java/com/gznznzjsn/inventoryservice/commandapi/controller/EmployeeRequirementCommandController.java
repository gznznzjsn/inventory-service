package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EmployeeRequirementCommandService;
import com.gznznzjsn.inventoryservice.core.web.dto.EmployeeRequirementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories/{inventoryId}/employee-requirements")
public class EmployeeRequirementCommandController {

    private final EmployeeRequirementCommandService employeeRequirementCommandService;

    @PostMapping
    public Mono<UUID> create(@RequestBody EmployeeRequirementDto employeeRequirementDto, @PathVariable UUID inventoryId) {
        return employeeRequirementCommandService.create(new EmployeeRequirementCreateCommand(
                inventoryId,
                employeeRequirementDto.specialization(),
                employeeRequirementDto.equipmentName()
        ));
    }

}

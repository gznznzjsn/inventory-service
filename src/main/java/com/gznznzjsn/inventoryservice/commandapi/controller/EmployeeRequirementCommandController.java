package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.EmployeeRequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EmployeeRequirementCmdService;
import com.gznznzjsn.inventoryservice.core.web.dto.EmployeeRequirementDto;
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
@RequestMapping("/inventory-api/v1/"
                + "inventories/{inventoryId}/"
                + "employee-requirements")
public class EmployeeRequirementCommandController {

    private final EmployeeRequirementCmdService requirementService;

    @PostMapping
    public Mono<UUID> create(
            final @RequestBody EmployeeRequirementDto dto,
            final @PathVariable UUID inventoryId
    ) {
        return requirementService.create(
                new EmployeeRequirementCreateCommand(
                        inventoryId,
                        dto.specialization(),
                        dto.name()
                )
        );
    }

}

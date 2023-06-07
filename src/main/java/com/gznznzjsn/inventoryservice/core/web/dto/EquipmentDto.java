package com.gznznzjsn.inventoryservice.core.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record EquipmentDto(

        UUID id,

        @NotBlank
        String name,

        @NotBlank
        String manufacturer,

        @NotBlank
        String description,

        EmployeeDto owner

) {
}

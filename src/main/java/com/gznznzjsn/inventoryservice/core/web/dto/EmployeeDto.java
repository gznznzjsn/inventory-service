package com.gznznzjsn.inventoryservice.core.web.dto;

import com.gznznzjsn.inventoryservice.core.model.Specialization;

import java.util.UUID;

public record EmployeeDto(
        UUID id,
        String name,
        Specialization specialization
) {
}

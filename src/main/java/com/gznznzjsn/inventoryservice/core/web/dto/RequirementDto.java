package com.gznznzjsn.inventoryservice.core.web.dto;

import com.gznznzjsn.inventoryservice.core.model.Specialization;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequirementDto(

        UUID id,

        @NotNull
        Specialization specialization,

        @NotNull
        String name

) {
}

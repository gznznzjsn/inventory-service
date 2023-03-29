package com.gznznzjsn.inventoryservice.core.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmployeeRequirementDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        @NotNull
        Specialization specialization,
        @NotNull
        String equipmentName

) {
}

package com.gznznzjsn.inventoryservice.core.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EquipmentDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        @NotNull
        String name

) {
}

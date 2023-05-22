package com.gznznzjsn.inventoryservice.core.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record EquipmentDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,

        @NotBlank
        String name,

        @NotBlank
        String manufacturer,

        @NotBlank
        String description

) {
}

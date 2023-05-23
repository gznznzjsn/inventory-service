package com.gznznzjsn.inventoryservice.queryapi.query;

import java.util.UUID;

public record GetEquipmentAutocompleteQuery(

        UUID inventoryId,
        Integer from,
        Integer size,
        String query

) {
}

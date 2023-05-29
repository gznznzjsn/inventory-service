package com.gznznzjsn.inventoryservice.queryapi.query.handler;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import reactor.core.publisher.Flux;

public interface EquipmentQueryHandler {

    /**
     * Searches for {@link Equipment}, which is relevant to
     * {@link GetEquipmentAutocompleteQuery}. Passes query to
     * repository.
     *
     * @param query {@link GetEquipmentAutocompleteQuery}, which contains
     *              {@link com.gznznzjsn.inventoryservice.core.model.Inventory}
     *              id and input query
     * @return {@link Equipment}, relevant
     * to {@link GetEquipmentAutocompleteQuery}
     */
    Flux<Equipment> handle(GetEquipmentAutocompleteQuery query);

}

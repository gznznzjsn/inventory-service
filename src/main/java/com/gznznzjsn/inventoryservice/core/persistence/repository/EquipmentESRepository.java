package com.gznznzjsn.inventoryservice.core.persistence.repository;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import reactor.core.publisher.Flux;

public interface EquipmentESRepository {

    /**
     * Searches for {@link Equipment}, which
     * {@link com.gznznzjsn.inventoryservice.core.model.Inventory} id matches id
     * from {@link GetEquipmentAutocompleteQuery} and name, or manufacturer,
     * or description phrase has a prefix, that matches query
     * from {@link GetEquipmentAutocompleteQuery}.
     *
     * @param query {@link GetEquipmentAutocompleteQuery}, which contains
     *              {@link com.gznznzjsn.inventoryservice.core.model.Inventory}
     *              id and input query
     * @return {@link Equipment}, relevant
     * to {@link GetEquipmentAutocompleteQuery}
     */
    Flux<Equipment> getAutocomplete(GetEquipmentAutocompleteQuery query);

}

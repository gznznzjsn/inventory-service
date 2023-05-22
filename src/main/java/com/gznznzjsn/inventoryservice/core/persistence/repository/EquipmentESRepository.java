package com.gznznzjsn.inventoryservice.core.persistence.repository;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import reactor.core.publisher.Flux;

public interface EquipmentESRepository {

    Flux<Equipment> getAutocomplete(GetEquipmentAutocompleteQuery query);

}

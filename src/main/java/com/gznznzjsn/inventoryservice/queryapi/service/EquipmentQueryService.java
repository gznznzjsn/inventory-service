package com.gznznzjsn.inventoryservice.queryapi.service;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentByCriteriaQuery;
import reactor.core.publisher.Flux;

public interface EquipmentQueryService {

    Flux<Equipment> retrieveByCriteria(GetEquipmentByCriteriaQuery query);

}

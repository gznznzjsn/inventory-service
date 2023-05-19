package com.gznznzjsn.inventoryservice.queryapi.query.handler;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentByCriteriaQuery;
import reactor.core.publisher.Flux;

public interface EquipmentQueryHandler {

    Flux<Equipment> handle(GetEquipmentByCriteriaQuery query);

}

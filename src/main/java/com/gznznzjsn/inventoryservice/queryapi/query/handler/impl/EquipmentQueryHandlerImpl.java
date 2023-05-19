package com.gznznzjsn.inventoryservice.queryapi.query.handler.impl;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentESRepository;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentByCriteriaQuery;
import com.gznznzjsn.inventoryservice.queryapi.query.handler.EquipmentQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class EquipmentQueryHandlerImpl implements EquipmentQueryHandler {

    private final EquipmentESRepository repository;

    @Override
    public Flux<Equipment> handle(GetEquipmentByCriteriaQuery query) {
        repository.trySearch();
        return Flux.empty();
    }

}

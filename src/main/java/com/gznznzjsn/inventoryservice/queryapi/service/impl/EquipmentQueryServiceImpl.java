package com.gznznzjsn.inventoryservice.queryapi.service.impl;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import com.gznznzjsn.inventoryservice.queryapi.query.handler.EquipmentQueryHandler;
import com.gznznzjsn.inventoryservice.queryapi.service.EquipmentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EquipmentQueryServiceImpl implements EquipmentQueryService {

    private final EquipmentQueryHandler equipmentHandler;

    @Override
    public Flux<Equipment> retrieveAutocomplete(
            final GetEquipmentAutocompleteQuery query
    ) {
        return equipmentHandler.handle(query);
    }

}

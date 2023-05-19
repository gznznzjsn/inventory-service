package com.gznznzjsn.inventoryservice.queryapi.controller;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentByCriteriaQuery;
import com.gznznzjsn.inventoryservice.queryapi.service.EquipmentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-api/v1/inventories/{inventoryId}/equipment")
public class EquipmentQueryController {

    private final EquipmentQueryService equipmentService;

    @GetMapping("")
    public Flux<Equipment> get(final @PathVariable UUID inventoryId) {  //todo fix dto
        return equipmentService
                .retrieveByCriteria(new GetEquipmentByCriteriaQuery());
    }

}

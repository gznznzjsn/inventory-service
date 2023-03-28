package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EquipmentEventHandler {

    private final EquipmentRepository repository;

    @EventHandler
    public void on(EquipmentCreatedEvent event) {
        Mono.just(event)
                .flatMap(e -> repository.save(Equipment.builder()
                        .id(e.getEquipmentId())
                        .owner(Employee.builder()
                                .id(e.getOwnerId())
                                .build())
                        .inventory(Inventory.builder()
                                .id(e.getInventoryId())
                                .build())
                        .equipmentName(e.getEquipmentName())
                        .isNew(true)
                        .build()))
                .subscribe();
    }

}

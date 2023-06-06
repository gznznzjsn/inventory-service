package com.gznznzjsn.inventoryservice.commandapi.event.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.handler.EquipmentEventHandler;
import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.model.exception.ResourceNotFoundException;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EquipmentEventHandlerAxon implements EquipmentEventHandler {

    private final EquipmentRepository repository;

    @Override
    @EventHandler
    public void on(final EquipmentCreatedEvent event) {
        repository.save(
                        Equipment.builder()
                                .id(event.getEquipmentId())
                                .owner(
                                        Employee.builder()
                                                .id(event.getOwnerId())
                                                .build()
                                )
                                .inventory(
                                        Inventory.builder()
                                                .id(event.getInventoryId())
                                                .build()
                                )
                                .name(event.getName())
                                .manufacturer(event.getManufacturer())
                                .description(event.getDescription())
                                .isNew(true)
                                .build()
                )
                .subscribe();
    }

    @Override
    @EventHandler
    public void on(final EquipmentOwnerAddedEvent event) {
        repository.findById(event.getEquipmentId())
                .doOnNext(equipment -> equipment.setOwner(
                        Employee.builder()
                                .id(event.getOwnerId())
                                .build()
                ))
                .flatMap(repository::save)
                .subscribe();
    }

}

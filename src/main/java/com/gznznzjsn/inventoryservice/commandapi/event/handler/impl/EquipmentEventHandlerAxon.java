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
        Mono.just(event)
                .flatMap(e -> repository.save(
                        Equipment.builder()
                                .id(e.getEquipmentId())
                                .owner(
                                        Employee.builder()
                                                .id(e.getOwnerId())
                                                .build()
                                )
                                .inventory(
                                        Inventory.builder()
                                                .id(e.getInventoryId())
                                                .build()
                                )
                                .name(e.getName())
                                .manufacturer(e.getManufacturer())
                                .description(e.getDescription())
                                .isNew(true)
                                .build()
                ))
                .subscribe();
    }

    @Override
    @EventHandler
    public void on(final EquipmentOwnerAddedEvent event) {
        repository.findById(event.getEquipmentId())
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                "Equipment with id = "
                                + event.getEquipmentId()
                                + " not found!"
                        )
                ))
                .map(equipmentFromRepository -> {
                    if (event.getOwnerId() != null) {
                        equipmentFromRepository.setOwner(
                                Employee.builder()
                                        .id(event.getOwnerId())
                                        .build()
                        );
                    }
                    return equipmentFromRepository;
                })
                .flatMap(repository::save)
                .subscribe();
    }

}

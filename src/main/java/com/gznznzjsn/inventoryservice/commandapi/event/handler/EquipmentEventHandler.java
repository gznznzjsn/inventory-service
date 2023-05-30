package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.model.exception.ResourceNotFoundException;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EquipmentEventHandler {

    private final EquipmentRepository repository;

    /**
     * Handles {@link EquipmentCreatedEvent} extracts all fields from
     * it, initializes all fields of
     * {@link Equipment} and
     * saves it to {@link EquipmentRepository}.
     *
     * @param event provides fields for new instance of
     *              {@link Equipment}
     */
    @EventHandler
    public void on(final EquipmentCreatedEvent event) {
        Mono.just(event)
                .flatMap(e -> repository.save(Equipment.builder()
                        .id(e.getEquipmentId())
                        .owner(Employee.builder()
                                .id(e.getOwnerId())
                                .build())
                        .inventory(Inventory.builder()
                                .id(e.getInventoryId())
                                .build())
                        .name(e.getName())
                        .manufacturer(e.getManufacturer())
                        .description(e.getDescription())
                        .isNew(true)
                        .build()))
                .subscribe();
    }

    /**
     * Handles {@link EquipmentOwnerAddedEvent} extracts
     * {@link Equipment} identity, owner identity, finds {@link Equipment} and
     * sets owner.
     *
     * @param event provides {@link Equipment} and owner identity
     */
    @EventHandler
    public void on(final EquipmentOwnerAddedEvent event) {
        get(event.getEquipmentId())
                .map(equipmentFromRepository -> {
                    if (event.getOwnerId() != null) {
                        equipmentFromRepository.setOwner(Employee.builder()
                                .id(event.getOwnerId())
                                .build());
                    }
                    return equipmentFromRepository;
                })
                .flatMap(repository::save)
                .subscribe();
    }

    /**
     * Finds {@link Equipment} by its identity.
     *
     * @param equipmentId identity of required {@link Equipment}
     * @return {@link Mono} with found {@link Equipment}
     * @throws ResourceNotFoundException if {@link Equipment} not found
     */
    public Mono<Equipment> get(final UUID equipmentId) {
        return repository.findById(equipmentId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                "Equipment with id = "
                                + equipmentId
                                + " not found!"
                        ))
                );
    }

}

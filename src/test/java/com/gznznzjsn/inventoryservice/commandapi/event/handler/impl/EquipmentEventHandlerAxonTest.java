package com.gznznzjsn.inventoryservice.commandapi.event.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.EquipmentOwnerAddedEvent;
import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentEventHandlerAxonTest {

    @InjectMocks
    private EquipmentEventHandlerAxon handler;

    @Mock
    private EquipmentRepository repository;

    @Test
    public void onEquipmentCreatedEvent() {
        var inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        var equipmentId = UUID.fromString(
                "69f9995e-8d8e-4148-a2fc-a554ce40e5b4"
        );
        var name = "shovel";
        var manufacturer = "Shovelini and Co";
        var description = "Fake shovel - fake description";
        var event = new EquipmentCreatedEvent(
                inventoryId,
                equipmentId,
                name,
                manufacturer,
                description,
                null
        );
        var equipment = Equipment.builder()
                .id(equipmentId)
                .inventory(
                        Inventory.builder()
                                .id(inventoryId)
                                .build()
                )
                .name(name)
                .manufacturer(manufacturer)
                .description(description)
                .owner(new Employee())
                .isNew(true)
                .build();
        //noinspection ReactiveStreamsUnusedPublisher
        doAnswer(invocation -> {
            Equipment e = invocation.getArgument(0);
            //noinspection ReactiveStreamsUnusedPublisher
            return Mono.just(e);
        }).when(repository).save(any(Equipment.class));

        handler.on(event);

        verify(repository).save(equipment);
    }

    @Test
    public void onEquipmentOwnerAddedEvent() {
        var inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        var equipmentId = UUID.fromString(
                "69f9995e-8d8e-4148-a2fc-a554ce40e5b4"
        );
        var name = "shovel";
        var manufacturer = "Shovelini and Co";
        var description = "Fake shovel - fake description";
        var ownerId = UUID.fromString(
                "380aae22-0648-4eac-9794-713b74153c80"
        );
        var event = new EquipmentOwnerAddedEvent(equipmentId, ownerId);
        var equipment = Equipment.builder()
                .id(equipmentId)
                .inventory(
                        Inventory.builder()
                                .id(inventoryId)
                                .build()
                )
                .name(name)
                .manufacturer(manufacturer)
                .description(description)
                .owner(
                        Employee.builder()
                                .id(ownerId)
                                .build()
                )
                .isNew(true)
                .build();
        //noinspection ReactiveStreamsUnusedPublisher
        doAnswer(invocation -> {
            UUID id = invocation.getArgument(0);
            //noinspection ReactiveStreamsUnusedPublisher
            return Mono.just(
                    Equipment.builder()
                            .id(id)
                            .inventory(
                                    Inventory.builder()
                                            .id(inventoryId)
                                            .build()
                            )
                            .name(name)
                            .manufacturer(manufacturer)
                            .description(description)
                            .owner(new Employee())
                            .isNew(true)
                            .build()
            );
        }).when(repository).findById(any(UUID.class));
        //noinspection ReactiveStreamsUnusedPublisher
        doAnswer(invocation -> {
            Equipment e = invocation.getArgument(0);
            //noinspection ReactiveStreamsUnusedPublisher
            return Mono.just(e);
        }).when(repository).save(any(Equipment.class));

        handler.on(event);

        verify(repository).findById(equipmentId);
        verify(repository).save(equipment);
    }

}
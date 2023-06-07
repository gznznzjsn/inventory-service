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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class EquipmentEventHandlerAxonTest {

    @InjectMocks
    private EquipmentEventHandlerAxon handler;

    @Mock
    private EquipmentRepository repository;

    @Test
    public void onEquipmentCreatedEvent() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        UUID equipmentId = UUID.fromString(
                "69f9995e-8d8e-4148-a2fc-a554ce40e5b4"
        );
        String name = "shovel";
        String manufacturer = "Shovelini and Co";
        String description = "Fake shovel - fake description";
        var event = new EquipmentCreatedEvent(
                inventoryId, equipmentId, name, manufacturer, description, null
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
        Mockito.when(repository.save(Mockito.any(Equipment.class)))
                .thenAnswer(invocation -> {
                    Equipment e = invocation.getArgument(0);
                    //noinspection ReactiveStreamsUnusedPublisher
                    return Mono.just(e);
                });

        handler.on(event);

        Mockito.verify(repository).save(equipment);
    }

    @Test
    public void onEquipmentOwnerAddedEvent() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        UUID equipmentId = UUID.fromString(
                "69f9995e-8d8e-4148-a2fc-a554ce40e5b4"
        );
        String  name = "shovel";
        String  manufacturer = "Shovelini and Co";
        String  description = "Fake shovel - fake description";
        UUID ownerId = UUID.fromString(
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
        Mockito.when(repository.findById(Mockito.any(UUID.class)))
                .thenAnswer(invocation -> {
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
                });
        //noinspection ReactiveStreamsUnusedPublisher
        Mockito.doAnswer(invocation -> {
            Equipment e = invocation.getArgument(0);
            //noinspection ReactiveStreamsUnusedPublisher
            return Mono.just(e);
        }).when(repository).save(Mockito.any(Equipment.class));

        handler.on(event);

        Mockito.verify(repository).findById(equipmentId);
        Mockito.verify(repository).save(equipment);
    }

}

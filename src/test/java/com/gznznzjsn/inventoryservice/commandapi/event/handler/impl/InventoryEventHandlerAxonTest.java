package com.gznznzjsn.inventoryservice.commandapi.event.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryEventHandlerAxonTest {

    @InjectMocks
    private InventoryEventHandlerAxon handler;

    @Mock
    private InventoryRepository repository;

    @Test
    public void onInventoryCreatedEvent() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        var event = new InventoryCreatedEvent(inventoryId);
        var inventory = Inventory.builder()
                .id(inventoryId)
                .isNew(true)
                .build();
        when(repository.save(any(Inventory.class)))
                .thenAnswer(invocation -> {
                    Inventory i = invocation.getArgument(0);
                    //noinspection ReactiveStreamsUnusedPublisher
                    return Mono.just(i);
                });

        handler.on(event);

        verify(repository).save(inventory);
    }

    @Test
    public void onInventoryDeletedEvent() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        var event = new InventoryDeletedEvent(inventoryId);
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        handler.on(event);

        verify(repository).deleteById(inventoryId);
    }

}
package com.gznznzjsn.inventoryservice.commandapi.event.handler.impl;


import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.InventoryDeletedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.handler.InventoryEventHandler;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventHandlerAxon implements InventoryEventHandler {

    private final InventoryRepository repository;

    @Override
    @EventHandler
    public void on(final InventoryCreatedEvent event) {
        repository.save(Inventory.builder()
                        .id(event.getInventoryId())
                        .isNew(true)
                        .build())
                .subscribe();
    }

    @Override
    @EventHandler
    public void on(final InventoryDeletedEvent event) {
        repository.deleteById(event.getInventoryId()).subscribe();
    }

}

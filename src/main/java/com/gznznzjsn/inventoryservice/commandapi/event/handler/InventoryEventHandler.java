package com.gznznzjsn.inventoryservice.commandapi.event.handler;


import com.gznznzjsn.inventoryservice.commandapi.event.InventoryCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryEventHandler {

    private final InventoryRepository repository;

    @EventHandler
    public void on(InventoryCreatedEvent event) {
        Mono.just(event)
                .flatMap(e -> repository.save(Inventory.builder()
                        .id(event.getInventoryId())
                        .isNew(true)
                        .build()))
                .subscribe();
    }

}

package com.gznznzjsn.inventoryservice.commandapi.event.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.commandapi.event.handler.RequirementEventHandler;
import com.gznznzjsn.inventoryservice.core.model.Requirement;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequirementEventHandlerAxon implements RequirementEventHandler {

    private final RequirementRepository repository;

    @Override
    @EventHandler
    public void on(final RequirementCreatedEvent event) {
        Mono.just(event)
                .flatMap(e -> repository.save(Requirement.builder()
                        .id(e.getRequirementId())
                        .inventory(Inventory.builder()
                                .id(e.getInventoryId())
                                .build())
                        .name(e.getName())
                        .specialization(e.getSpecialization())
                        .isNew(true)
                        .build()))
                .subscribe();
    }

}

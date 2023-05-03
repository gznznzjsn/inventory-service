package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.EmployeeRequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EmployeeRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeRequirementEventHandler {

    private final EmployeeRequirementRepository repository;

    @EventHandler
    public void on(final EmployeeRequirementCreatedEvent event) {
        Mono.just(event)
                .flatMap(e -> repository.save(EmployeeRequirement.builder()
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

package com.gznznzjsn.inventoryservice.commandapi.event.handler;

import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Requirement;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.persistence.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequirementEventHandler {

    private final RequirementRepository repository;

    /**Handles {@link RequirementCreatedEvent}, extracts values, builds
     * {@link Requirement} and saves it to
     * {@link RequirementRepository}.
     *
     * @param event provides values to initialize new
     * {@link Requirement}
     */
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

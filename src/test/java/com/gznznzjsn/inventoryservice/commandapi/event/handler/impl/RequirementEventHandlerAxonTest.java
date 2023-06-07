package com.gznznzjsn.inventoryservice.commandapi.event.handler.impl;

import com.gznznzjsn.inventoryservice.commandapi.event.RequirementCreatedEvent;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.model.Requirement;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import com.gznznzjsn.inventoryservice.core.persistence.repository.RequirementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RequirementEventHandlerAxonTest {

    @InjectMocks
    private RequirementEventHandlerAxon handler;

    @Mock
    private RequirementRepository repository;

    @Test
    public void onRequirementCreatedEvent() {
        UUID inventoryId = UUID.fromString(
                "73ba3fbf-0738-4700-94b2-04ea02cf7114"
        );
        UUID requirementId = UUID.fromString(
                "69f9995e-8d8e-4148-a2fc-a554ce40e5b4"
        );
        String name = "shovel";
        var specialization = Specialization.CLEANER;
        var event = new RequirementCreatedEvent(
                inventoryId, requirementId, specialization, name
        );
        var requirement = Requirement.builder()
                .id(requirementId)
                .inventory(
                        Inventory.builder()
                                .id(inventoryId)
                                .build()
                )
                .specialization(specialization)
                .name(name)
                .isNew(true)
                .build();
        Mockito.when(repository.save(any(Requirement.class)))
                .thenAnswer(invocation -> {
                    Requirement r = invocation.getArgument(0);
                    //noinspection ReactiveStreamsUnusedPublisher
                    return Mono.just(r);
                });

        handler.on(event);

        Mockito.verify(repository).save(requirement);
    }

}

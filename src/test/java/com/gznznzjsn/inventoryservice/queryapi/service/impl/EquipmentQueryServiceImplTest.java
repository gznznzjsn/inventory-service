package com.gznznzjsn.inventoryservice.queryapi.service.impl;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import com.gznznzjsn.inventoryservice.queryapi.query.handler.EquipmentQueryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipmentQueryServiceImplTest {

    @InjectMocks
    private EquipmentQueryServiceImpl service;

    @Mock
    private EquipmentQueryHandler handler;

    @Test
    public void retrieveAutocomplete() {
        var equipment1 = Equipment.builder()
                .id(UUID.fromString(
                        "3ea20210-2ff0-4e7b-bef0-4fb01707abdb"
                ))
                .build();
        var equipment2 = Equipment.builder()
                .id(UUID.fromString(
                        "121b6945-f51f-4111-b05d-f2d8e13076d8"
                ))
                .build();
        var query = new GetEquipmentAutocompleteQuery(
                UUID.fromString(
                        "d4704dcf-0217-4215-b1fa-3f877e743911"
                ),
                0,
                3,
                "FAKE QUERY"
        );
        when(handler.handle(any(GetEquipmentAutocompleteQuery.class)))
                .thenReturn(Flux.just(equipment1, equipment2));

        Flux<Equipment> equipment = service.retrieveAutocomplete(query);

        verify(handler).handle(query);
        StepVerifier.create(equipment)
                .expectNext(equipment1, equipment2)
                .expectComplete()
                .verify();
    }

}
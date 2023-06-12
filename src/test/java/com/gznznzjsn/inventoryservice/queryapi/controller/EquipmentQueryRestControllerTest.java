package com.gznznzjsn.inventoryservice.queryapi.controller;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import com.gznznzjsn.inventoryservice.core.web.dto.mapper.EquipmentMapper;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import com.gznznzjsn.inventoryservice.queryapi.service.EquipmentQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@WebFluxTest(EquipmentQueryRestController.class)
class EquipmentQueryRestControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private EquipmentQueryService service;

    @MockBean
    private EquipmentMapper mapper;

    @Test
    public void getAutocomplete() {
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
        Mockito.when(service.retrieveAutocomplete(
                        any(GetEquipmentAutocompleteQuery.class)
                ))
                .thenReturn(Flux.just(equipment1, equipment2));
        Mockito.when(mapper.toDto(any(Equipment.class)))
                .thenAnswer(invocation -> new EquipmentDto(
                        ((Equipment) invocation.getArgument(0)).getId(),
                        null,
                        null,
                        null,
                        null
                ));

        client.get()
                .uri(String.format(
                        "/inventory-api/v1"
                        + "/inventories/%s"
                        + "/equipment/autocomplete?from=%d&size=%d&query=%s",
                        query.inventoryId(),
                        query.from(),
                        query.size(),
                        query.query()
                ))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(EquipmentDto.class)
                .hasSize(2)
                .value(l -> {
                    assertEquals(equipment1.getId(), l.get(0).id());
                    assertEquals(equipment2.getId(), l.get(1).id());
                });

        verify(service).retrieveAutocomplete(query);
        verify(mapper, times(2)).toDto(any(Equipment.class));
    }

}

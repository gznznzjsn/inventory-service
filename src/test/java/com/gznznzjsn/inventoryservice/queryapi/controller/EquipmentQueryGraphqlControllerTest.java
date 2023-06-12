package com.gznznzjsn.inventoryservice.queryapi.controller;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import com.gznznzjsn.inventoryservice.core.web.dto.mapper.EquipmentMapper;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import com.gznznzjsn.inventoryservice.queryapi.service.EquipmentQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@GraphQlTest(EquipmentQueryGraphqlController.class)
class EquipmentQueryGraphqlControllerTest {

    @Autowired
    private GraphQlTester tester;

    @MockBean
    private EquipmentQueryService service;

    @MockBean
    private EquipmentMapper mapper;

    @Test
    public void getAutocomplete() {
        UUID inventoryId = UUID.fromString(
                "45473931-8232-4f95-b42c-60b6851c66b1"
        );
        var equipment1 = Equipment.builder()
                .id(UUID.fromString(
                        "3ea20210-2ff0-4e7b-bef0-4fb01707abdb"
                ))
                .name("NAME 1")
                .description("DESCRIPTION 1")
                .manufacturer("MANUFACTURER 1")
                .inventory(
                        Inventory.builder()
                                .id(inventoryId)
                                .build()
                )
                .build();
        var equipment2 = Equipment.builder()
                .id(UUID.fromString(
                        "121b6945-f51f-4111-b05d-f2d8e13076d8"
                ))
                .name("NAME 2")
                .description("DESCRIPTION 2")
                .manufacturer("MANUFACTURER 2")
                .inventory(
                        Inventory.builder()
                                .id(inventoryId)
                                .build()
                )
                .build();
        var query = new GetEquipmentAutocompleteQuery(
                inventoryId,
                0,
                3,
                "FAKE QUERY"
        );
        Mockito.when(service.retrieveAutocomplete(
                        any(GetEquipmentAutocompleteQuery.class)
                ))
                .thenReturn(Flux.just(equipment1, equipment2));
        Mockito.when(mapper.toDto(any(Equipment.class)))
                .thenAnswer(invocation -> {
                    Equipment e = invocation.getArgument(0);
                    return new EquipmentDto(
                            e.getId(),
                            e.getName(),
                            e.getManufacturer(),
                            e.getDescription(),
                            null
                    );
                });
//        language=GraphQL
        String document = """
                query {
                    getEquipmentWithAutocomplete(
                    query: "%s"
                     inventoryId: "%s"
                     from: %d
                     size: %d
                    ){
                     id
                     name
                     description
                     manufacturer
                    }
                }
                """
                .formatted(
                        query.query(),
                        query.inventoryId(),
                        query.from(),
                        query.size()
                );
        tester.document(document)
                .execute()
                .path("getEquipmentWithAutocomplete")
                .entityList(EquipmentDto.class)
                .hasSize(2)
                .contains(
                        new EquipmentDto(
                                equipment1.getId(),
                                equipment1.getName(),
                                equipment1.getManufacturer(),
                                equipment1.getDescription(),
                                null
                        ),
                        new EquipmentDto(
                                equipment2.getId(),
                                equipment2.getName(),
                                equipment2.getManufacturer(),
                                equipment2.getDescription(),
                                null
                        )

                );

        verify(service).retrieveAutocomplete(query);
        verify(mapper, times(2)).toDto(any(Equipment.class));
    }

}

package com.gznznzjsn.inventoryservice.queryapi.controller;

import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import com.gznznzjsn.inventoryservice.core.web.dto.mapper.EquipmentMapper;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import com.gznznzjsn.inventoryservice.queryapi.service.EquipmentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EquipmentQueryGraphqlController {

    private final EquipmentQueryService equipmentService;
    private final EquipmentMapper mapper;

    /**
     * Endpoint for GraphQL.
     * Handles search queries to offer relevant
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}.
     * Wraps parameters from request in {@link GetEquipmentAutocompleteQuery}
     * and pases it to {@link EquipmentQueryService}.
     *
     * @param inventoryId id of {@link
     *                    com.gznznzjsn.inventoryservice.core.model.Inventory},
     *                    which contains needed {@link
     *                    com.gznznzjsn.inventoryservice.core.model.Equipment}
     * @param from        start of page
     * @param size        size of page
     * @param query       search query of a user, which must be autocompleted
     * @return {@link Flux} page with the most relevant
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}
     */
    @QueryMapping(name = "getEquipmentWithAutocomplete")
    public Flux<EquipmentDto> getAutocomplete(
            final @Argument UUID inventoryId,
            final @Argument Integer from,
            final @Argument Integer size,
            final @Argument String query
    ) {
        return equipmentService
                .retrieveAutocomplete(new GetEquipmentAutocompleteQuery(
                        inventoryId, from, size, query
                ))
                .map(mapper::toDto);
    }

}

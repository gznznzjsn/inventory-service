package com.gznznzjsn.inventoryservice.commandapi.controller;


import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.RequirementCmdService;
import com.gznznzjsn.inventoryservice.core.web.dto.RequirementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RequirementCommandGraphqlController {

    private final RequirementCmdService requirementService;

    /**
     * Endpoint for GraphQL.
     * With the help of {@link RequirementCmdService} creates
     * {@link com.gznznzjsn.inventoryservice.core.model.Equipment}.
     *
     * @param dto         provides values to initialize
     *                    entity  fields
     * @param inventoryId id of target aggregate, which will store created
     *                    entity
     * @return {@link Mono} with {@link UUID} of target aggregate
     */
    @MutationMapping(name = "createRequirement")
    public Mono<UUID> create(
            final @Arguments RequirementDto dto,
            final @Argument UUID inventoryId
    ) {
        return requirementService.create(
                new RequirementCreateCommand(
                        inventoryId,
                        null,
                        dto.specialization(),
                        dto.name()
                )
        );
    }

}

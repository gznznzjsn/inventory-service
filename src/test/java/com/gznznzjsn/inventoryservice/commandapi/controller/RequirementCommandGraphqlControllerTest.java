package com.gznznzjsn.inventoryservice.commandapi.controller;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.RequirementCmdService;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@GraphQlTest(RequirementCommandGraphqlController.class)
class RequirementCommandGraphqlControllerTest {

    @Autowired
    private GraphQlTester tester;

    @MockBean
    private RequirementCmdService service;

    @Test
    public void createRequirement() {
        var command = new RequirementCreateCommand(
                UUID.fromString(
                        "73ba3fbf-0738-4700-94b2-04ea02cf7114"
                ),
                null,
                Specialization.CLEANER,
                "shovel"
        );
        Mockito.when(service.create(any(RequirementCreateCommand.class)))
                .thenReturn(Mono.empty());
//        language=GraphQL
        String document = """
                mutation {
                    createRequirement(
                        inventoryId: "%s"
                        specialization: %s
                        name: "%s"
                    )
                }
                """.formatted(
                command.getInventoryId(),
                command.getSpecialization(),
                command.getName()
        );

        tester.document(document)
                .execute()
                .path("createRequirement")
                .valueIsNull();

        verify(service).create(command);
    }

}
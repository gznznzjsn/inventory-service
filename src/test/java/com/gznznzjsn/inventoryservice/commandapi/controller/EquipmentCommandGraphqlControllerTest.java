package com.gznznzjsn.inventoryservice.commandapi.controller;

import com.gznznzjsn.inventoryservice.commandapi.command.EquipmentCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.EquipmentCmdService;
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

@GraphQlTest(EquipmentCommandGraphqlController.class)
class EquipmentCommandGraphqlControllerTest {

    @Autowired
    private GraphQlTester tester;

    @MockBean
    private EquipmentCmdService service;

    @Test
    public void createEquipment() {
        var command = new EquipmentCreateCommand(
                UUID.fromString(
                        "73ba3fbf-0738-4700-94b2-04ea02cf7114"
                ),
                null,
                "shovel",
                "Shovelini and Co",
                "Fake shovel - fake description"
        );
        Mockito.when(service.create(any(EquipmentCreateCommand.class)))
                .thenReturn(Mono.empty());
//        language=GraphQL
        String document = """
                mutation {
                    createEquipment(
                        inventoryId: "%s"
                        name: "%s"
                        manufacturer: "%s"
                        description: "%s"
                    )
                }
                """.formatted(
                command.getInventoryId(),
                command.getName(),
                command.getManufacturer(),
                command.getDescription()
        );

        tester.document(document)
                .execute()
                .path("createEquipment")
                .valueIsNull();

        verify(service).create(command);
    }

}

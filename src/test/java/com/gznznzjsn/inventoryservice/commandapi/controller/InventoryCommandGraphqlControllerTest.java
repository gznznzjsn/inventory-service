package com.gznznzjsn.inventoryservice.commandapi.controller;

import com.gznznzjsn.inventoryservice.commandapi.command.InventoryCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.command.InventoryDeleteCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.InventoryCmdService;
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

@GraphQlTest(InventoryCommandGraphqlController.class)
class InventoryCommandGraphqlControllerTest {

    @Autowired
    private GraphQlTester tester;

    @MockBean
    private InventoryCmdService service;

    @Test
    public void createInventory() {
        var command = new InventoryCreateCommand(null);
        Mockito.when(service.create(any(InventoryCreateCommand.class)))
                .thenReturn(Mono.empty());
//        language=GraphQL
        String document = """
                mutation {
                    createInventory
                }
                """;

        tester.document(document)
                .execute()
                .path("createInventory")
                .valueIsNull();

        verify(service).create(command);
    }

    @Test
    public void deleteInventory() {
        var command = new InventoryDeleteCommand(
                UUID.fromString(
                        "73ba3fbf-0738-4700-94b2-04ea02cf7114"
                )
        );
        Mockito.when(service.delete(any(InventoryDeleteCommand.class)))
                .thenReturn(Mono.empty());
//        language=GraphQL
        String document = """
                mutation {
                    deleteInventory(id: "%s")
                }
                """.formatted(command.getInventoryId());

        tester.document(document)
                .execute()
                .path("deleteInventory")
                .valueIsNull();

        verify(service).delete(command);
    }

}
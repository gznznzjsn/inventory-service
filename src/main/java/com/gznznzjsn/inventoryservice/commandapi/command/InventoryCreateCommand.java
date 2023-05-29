package com.gznznzjsn.inventoryservice.commandapi.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreateCommand {

    private UUID inventoryId;

}

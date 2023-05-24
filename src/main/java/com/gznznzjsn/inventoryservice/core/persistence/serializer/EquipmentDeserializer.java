package com.gznznzjsn.inventoryservice.core.persistence.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;

import java.io.IOException;
import java.util.UUID;

public class EquipmentDeserializer extends StdDeserializer<Equipment> {

    /**
     * Calls constructor of parent class {@link StdDeserializer}.
     *
     * @param vc class of the value, which needs to be deserialized
     */
    public EquipmentDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Equipment deserialize(
            final JsonParser parser,
            final DeserializationContext deserializer
    ) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        UUID employeeId = node.get("owner_id") == null
                ? null
                : UUID.fromString(node.get("owner_id").asText());
        return Equipment.builder()
                .id(UUID.fromString(node.get("equipment_id").asText()))
                .name(node.get("equipment_name").asText())
                .manufacturer(node.get("manufacturer").asText())
                .description(node.get("description").asText())
                .inventory(
                        Inventory.builder()
                                .id(UUID.fromString(
                                        node.get("inventory_id").asText()
                                ))
                                .build()
                )
                .owner(
                        Employee.builder()
                                .id(employeeId)
                                .build()
                )
                .build();
    }

}

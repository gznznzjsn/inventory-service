package com.gznznzjsn.inventoryservice.core.persistence.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class EquipmentDeserializer extends StdDeserializer<Equipment> {

    public EquipmentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Equipment deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        return Equipment.builder()
                .id(UUID.fromString(node.get("equipment_id").asText()))
                .name(node.get("equipment_id").asText())
                .inventory(Inventory.builder()
                        .id(UUID.randomUUID())
                        .build())
                .owner(Employee.builder()
                        .id(UUID.randomUUID())
                        .build())
                .build();
    }

}

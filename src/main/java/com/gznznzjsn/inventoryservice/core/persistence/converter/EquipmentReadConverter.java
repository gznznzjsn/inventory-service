package com.gznznzjsn.inventoryservice.core.persistence.converter;


import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.UUID;

@ReadingConverter
public class EquipmentReadConverter implements Converter<Row, Equipment> {

    @Override
    public Equipment convert(final Row source) {
        return Equipment.builder()
                .id(source.get("equipment_id", UUID.class))
                .inventory(Inventory.builder()
                        .id(source.get("inventory_id", UUID.class))
                        .build()
                )
                .name(source.get("equipment_name", String.class))
                .manufacturer(source.get("manufacturer", String.class))
                .description(source.get("description", String.class))
                .owner(Employee.builder()
                        .id(source.get("owner_id", UUID.class))
                        .build())
                .build();
    }

}

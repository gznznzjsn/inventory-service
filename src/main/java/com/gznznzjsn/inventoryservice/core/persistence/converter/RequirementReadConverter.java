package com.gznznzjsn.inventoryservice.core.persistence.converter;


import com.gznznzjsn.inventoryservice.core.model.Requirement;
import com.gznznzjsn.inventoryservice.core.model.Inventory;
import com.gznznzjsn.inventoryservice.core.model.Specialization;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.UUID;

@ReadingConverter
public class RequirementReadConverter implements Converter<Row, Requirement> {

    @Override
    public Requirement convert(final Row source) {
        return Requirement.builder()
                .id(source.get("requirement_id", UUID.class))
                .inventory(Inventory.builder()
                        .id(source.get("inventory_id", UUID.class))
                        .build()
                )
                .specialization(Specialization
                        .valueOf(source.get("specialization", String.class))
                )
                .name(source.get("equipment_name", String.class))
                .build();
    }

}

package com.gznznzjsn.inventoryservice.core.persistence.converter;


import com.gznznzjsn.inventoryservice.core.model.*;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.UUID;

@ReadingConverter
public class EmployeeRequirementReadConverter implements Converter<Row, EmployeeRequirement> {

    @Override
    public EmployeeRequirement convert(Row source) {
        return EmployeeRequirement.builder()
                .id(source.get("employee_requirement_id", UUID.class))
                .inventory(Inventory.builder()
                        .id(source.get("inventory_id", UUID.class))
                        .build()
                )
                .specialization(Specialization
                        .valueOf(source.get("specialization", String.class))
                )
                .equipmentName(source.get("equipment_name", String.class))
                .build();
    }

}
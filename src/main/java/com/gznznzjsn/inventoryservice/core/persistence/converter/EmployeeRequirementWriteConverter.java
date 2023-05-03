package com.gznznzjsn.inventoryservice.core.persistence.converter;

import com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class EmployeeRequirementWriteConverter
        implements Converter<EmployeeRequirement, OutboundRow> {

    @Override
    public OutboundRow convert(final EmployeeRequirement requirement) {
        OutboundRow row = new OutboundRow();
        if (requirement.getId() != null) {
            row.put(
                    "employee_requirement_id",
                    Parameter.from(requirement.getId())
            );
        }
        if (
                requirement.getInventory() != null
                && requirement.getInventory().getId() != null
        ) {
            row.put(
                    "inventory_id",
                    Parameter.from(requirement.getInventory().getId())
            );
        }
        if (requirement.getSpecialization() != null) {
            row.put(
                    "specialization",
                    Parameter.from(requirement.getSpecialization())
            );
        }
        if (requirement.getName() != null) {
            row.put(
                    "equipment_name",
                    Parameter.from(requirement.getName())
            );
        }
        return row;
    }

}

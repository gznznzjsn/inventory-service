package com.gznznzjsn.inventoryservice.core.persistence.converter;

import com.gznznzjsn.inventoryservice.core.model.EmployeeRequirement;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class EmployeeRequirementWriteConverter implements Converter<EmployeeRequirement, OutboundRow> {

    @Override
    public OutboundRow convert(EmployeeRequirement employeeRequirement) {
        OutboundRow row = new OutboundRow();
        if (employeeRequirement.getId() != null) {
            row.put("employee_requirement_id", Parameter.from(employeeRequirement.getId()));
        }
        if (employeeRequirement.getInventory() != null && employeeRequirement.getInventory().getId() != null) {
            row.put("inventory_id", Parameter.from(employeeRequirement.getInventory().getId()));
        }
        if (employeeRequirement.getSpecialization() != null) {
            row.put("specialization", Parameter.from(employeeRequirement.getSpecialization()));
        }
        if (employeeRequirement.getEquipmentName() != null) {
            row.put("equipment_name", Parameter.from(employeeRequirement.getEquipmentName()));
        }
        return row;
    }

}
package com.gznznzjsn.inventoryservice.core.persistence.converter;

import com.gznznzjsn.inventoryservice.core.model.Requirement;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class RequirementWriteConverter
        implements Converter<Requirement, OutboundRow> {

    /**Converts {@link Requirement} to {@link OutboundRow} to pass it to
     * SQL query further.
     *
     * @param requirement which will be converted
     * @return converted {@link Requirement}
     */
    @Override
    public OutboundRow convert(final Requirement requirement) {
        OutboundRow row = new OutboundRow();
        if (requirement.getId() != null) {
            row.put(
                    "requirement_id",
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

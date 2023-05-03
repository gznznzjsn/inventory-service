package com.gznznzjsn.inventoryservice.core.persistence.converter;

import com.gznznzjsn.inventoryservice.core.model.Equipment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class EquipmentWriteConverter
        implements Converter<Equipment, OutboundRow> {

    @Override
    public OutboundRow convert(final Equipment equipment) {
        OutboundRow row = new OutboundRow();
        if (equipment.getId() != null) {
            row.put(
                    "equipment_id",
                    Parameter.from(equipment.getId())
            );
        }
        if (equipment.getInventory() != null
            && equipment.getInventory().getId() != null) {
            row.put(
                    "inventory_id",
                    Parameter.from(equipment.getInventory().getId())
            );
        }
        if (equipment.getName() != null) {
            row.put(
                    "equipment_name",
                    Parameter.from(equipment.getName())
            );
        }
        if (equipment.getOwner() != null
            && equipment.getOwner().getId() != null) {
            row.put(
                    "owner_id",
                    Parameter.from(equipment.getOwner().getId())
            );
        }
        return row;
    }

}

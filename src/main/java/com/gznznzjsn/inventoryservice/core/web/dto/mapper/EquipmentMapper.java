package com.gznznzjsn.inventoryservice.core.web.dto.mapper;


import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.web.dto.EquipmentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EquipmentMapper {

    /**
     * @param dto dto of {@link Equipment}, which will be mapped to entity
     * @return {@link Equipment}, unwrapped from {@link EquipmentDto}
     */
    Equipment toEntity(EquipmentDto dto);

    /**
     * @param entity entity of {@link Equipment}, which will be wrapped in dto
     * @return {@link EquipmentDto}, mapped from {@link Equipment}
     */
    EquipmentDto toDto(Equipment entity);

}

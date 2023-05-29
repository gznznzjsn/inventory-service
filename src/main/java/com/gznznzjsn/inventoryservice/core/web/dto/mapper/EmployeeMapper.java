package com.gznznzjsn.inventoryservice.core.web.dto.mapper;


import com.gznznzjsn.inventoryservice.core.model.Employee;
import com.gznznzjsn.inventoryservice.core.web.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * @param dto dto of {@link Employee}, which will be mapped to entity
     * @return {@link Employee}, unwrapped from {@link EmployeeDto}
     */
    Employee toEntity(EmployeeDto dto);

    /**
     * @param entity entity of {@link Employee}, which will be wrapped in dto
     * @return {@link EmployeeDto}, mapped from {@link Employee}
     */
    EmployeeDto toDto(Employee entity);

}

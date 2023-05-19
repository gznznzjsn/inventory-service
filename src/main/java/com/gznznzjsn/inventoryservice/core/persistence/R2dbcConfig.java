package com.gznznzjsn.inventoryservice.core.persistence;

import com.gznznzjsn.inventoryservice.core.persistence.converter.EmployeeRequirementReadConverter;
import com.gznznzjsn.inventoryservice.core.persistence.converter.EmployeeRequirementWriteConverter;
import com.gznznzjsn.inventoryservice.core.persistence.converter.EquipmentReadConverter;
import com.gznznzjsn.inventoryservice.core.persistence.converter.EquipmentWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfig {

    /**
     * Collects all custom converters and registers list of them
     * in new instance of {@link R2dbcCustomConversions}.
     *
     * @return {@link R2dbcCustomConversions} with all
     * registered custom converters.
     */
    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new EmployeeRequirementReadConverter());
        converters.add(new EmployeeRequirementWriteConverter());
        converters.add(new EquipmentReadConverter());
        converters.add(new EquipmentWriteConverter());
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }

}

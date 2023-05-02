package com.gznznzjsn.inventoryservice.core.persistence;


import com.gznznzjsn.inventoryservice.core.persistence.converter.EmployeeRequirementReadConverter;
import com.gznznzjsn.inventoryservice.core.persistence.converter.EmployeeRequirementWriteConverter;
import com.gznznzjsn.inventoryservice.core.persistence.converter.EquipmentReadConverter;
import com.gznznzjsn.inventoryservice.core.persistence.converter.EquipmentWriteConverter;
import com.mongodb.client.MongoClient;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.messaging.StreamableMessageSource;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RepositoryConfig {

    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new EmployeeRequirementReadConverter());
        converters.add(new EmployeeRequirementWriteConverter());
        converters.add(new EquipmentReadConverter());
        converters.add(new EquipmentWriteConverter());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }

    @Autowired
    public void configureInitialTrackingToken(
            final EventProcessingConfigurer processingConfigurer
    ) {
        TrackingEventProcessorConfiguration tepConfig =
                TrackingEventProcessorConfiguration
                        .forSingleThreadedProcessing()
                        .andInitialTrackingToken(
                                StreamableMessageSource::createHeadToken
                        );
        processingConfigurer.registerTrackingEventProcessorConfiguration(
                config -> tepConfig
        );
    }

    @Bean
    public EventStorageEngine storageEngine(final MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(
                        DefaultMongoTemplate.builder()
                                .mongoDatabase(client)
                                .build()
                )
                .eventSerializer(JacksonSerializer.defaultSerializer())
                .snapshotSerializer(JacksonSerializer.defaultSerializer())
                .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(
            final EventStorageEngine storageEngine,
            final SpringAxonConfiguration configuration
    ) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.getObject().messageMonitor(
                        EventStore.class,
                        "eventStore"
                ))
                .build();
    }

}

package com.gznznzjsn.inventoryservice.core.persistence;

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

@Configuration
public class AxonConfig {

    /**
     * Adds {@link org.axonframework.eventhandling.TrackingToken} to
     * {@link TrackingEventProcessorConfiguration} and registers it.
     *
     * @param configurer registers new configuration
     */
    @Autowired
    public void configureInitialTrackingToken(
            final EventProcessingConfigurer configurer
    ) {
        TrackingEventProcessorConfiguration tepConfig =
                TrackingEventProcessorConfiguration
                        .forSingleThreadedProcessing()
                        .andInitialTrackingToken(
                                StreamableMessageSource::createHeadToken
                        );
        configurer.registerTrackingEventProcessorConfiguration(
                config -> tepConfig
        );
    }

    /**
     * Sets MongoDB as EventStorage and configures Serializers for it.
     *
     * @param client provides info about database
     * @return EventStorageEngine configured to store events in MongoDB
     */
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

    /**
     * Configures {@link EmbeddedEventStore}.
     *
     * @param engine        is used as {@link EventStorageEngine} for
     *                      {@link EmbeddedEventStore}
     * @param configuration is used for
     *                      {@link org.axonframework.monitoring.MessageMonitor}
     *                      creation
     * @return {@link EmbeddedEventStore} with configured storage engine and
     * {@link org.axonframework.monitoring.MessageMonitor}
     */
    @Bean
    public EmbeddedEventStore eventStore(
            final EventStorageEngine engine,
            final SpringAxonConfiguration configuration
    ) {
        return EmbeddedEventStore.builder()
                .storageEngine(engine)
                .messageMonitor(configuration.getObject().messageMonitor(
                        EventStore.class,
                        "eventStore"
                ))
                .build();
    }

}

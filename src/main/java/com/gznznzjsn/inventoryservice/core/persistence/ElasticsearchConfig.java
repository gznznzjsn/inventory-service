package com.gznznzjsn.inventoryservice.core.persistence;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.persistence.serializer.EquipmentDeserializer;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    /**
     * Creates low-level {@link RestClient}, {@link ObjectMapper} with custom
     * {@link EquipmentDeserializer} to construct {@link ElasticsearchTransport}
     * out of them, {@link ElasticsearchClient} out of it.
     *
     * @param port port number of Elastic search
     * @return {@link ElasticsearchClient} for communication with Elastic search
     */
    @Bean
    public ElasticsearchClient elasticsearchClient(
            @Value("${settings.elasticsearch.port}") final int port,
            @Value("${settings.elasticsearch.host}") final String host
    ) {
        EquipmentDeserializer deserializer =
                new EquipmentDeserializer(Equipment.class);
        SimpleModule module = new SimpleModule("EquipmentDeserializer");
        module.addDeserializer(Equipment.class, deserializer);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        RestClient restClient = RestClient.builder(
                new HttpHost(host, port)
        ).build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper(mapper)
        );
        return new ElasticsearchClient(transport);
    }


}

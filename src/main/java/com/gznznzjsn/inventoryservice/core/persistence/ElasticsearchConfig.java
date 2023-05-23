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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        EquipmentDeserializer deserializer =
                new EquipmentDeserializer(Equipment.class);
        SimpleModule module = new SimpleModule("EquipmentDeserializer");
        module.addDeserializer(Equipment.class, deserializer);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper(mapper)
        );
        return new ElasticsearchClient(transport);
    }


}

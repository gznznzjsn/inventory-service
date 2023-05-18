package com.gznznzjsn.inventoryservice.core.persistence.repository.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentESRepository;
import com.gznznzjsn.inventoryservice.core.persistence.serializer.EquipmentDeserializer;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EquipmentESRepositoryImpl implements EquipmentESRepository {

    @Override
    @SneakyThrows
    public void trySearch(){
        EquipmentDeserializer equipmentDeserializer = new EquipmentDeserializer(Equipment.class);
        SimpleModule module = new SimpleModule("CustomEquipmentDeserializer");
        module.addDeserializer(Equipment.class, equipmentDeserializer);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper(mapper));
        ElasticsearchClient client = new ElasticsearchClient(transport);
        String searchText = "NAME";
        SearchResponse<Equipment> searchResponse = client.search(s -> s
                        .index("equipment")
                        .query(q -> q
                                .match(t -> t
                                        .field("equipment_name")
                                        .query(searchText)
                                )
                        ),
                Equipment.class
        );
        List<Hit<Equipment>> hits = searchResponse.hits().hits();
        for (Hit<Equipment> hit : hits) {
            System.out.println(hit.source());
        }
    }

}

package com.gznznzjsn.inventoryservice.core.persistence.repository.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentESRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EquipmentESRepositoryImpl implements EquipmentESRepository {

    private final ElasticsearchClient client;

    @Override
    @SneakyThrows
    public void trySearch() {
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

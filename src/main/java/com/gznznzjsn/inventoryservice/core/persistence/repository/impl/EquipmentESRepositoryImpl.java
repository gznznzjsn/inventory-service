package com.gznznzjsn.inventoryservice.core.persistence.repository.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.gznznzjsn.inventoryservice.core.model.Equipment;
import com.gznznzjsn.inventoryservice.core.persistence.repository.EquipmentESRepository;
import com.gznznzjsn.inventoryservice.queryapi.query.GetEquipmentAutocompleteQuery;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EquipmentESRepositoryImpl implements EquipmentESRepository {

    private final ElasticsearchClient client;

    @Override
    @SneakyThrows
    public Flux<Equipment> getAutocomplete(GetEquipmentAutocompleteQuery query) {
        Query nameQuery = Query.of(qr -> qr
                .matchPhrasePrefix(p -> p
                        .field("equipment_name")
                        .query(query.query())
                        .slop(1)
                        .maxExpansions(10)
                ));
        Query manufacturerQuery = Query.of(qr -> qr.matchPhrasePrefix(
                p -> p.field("manufacturer")
                        .query(query.query())
                        .slop(2)
                        .maxExpansions(10)
        ));
        Query descriptionQuery = Query.of(qr -> qr.matchPhrasePrefix(
                p -> p.field("description")
                        .query(query.query())
                        .slop(2)
                        .maxExpansions(10))
        );
        SearchResponse<Equipment>
                searchResponse = client.search(
                s -> s.index("equipment")
                        .from(0)
                        .size(3)
                        .query(q -> q.bool(
                                b -> b.must(
//                                        Query.of(qr -> qr.match(p -> p
//                                                .field("inventory_id")
//                                                .query(
//                                                        query.inventoryId()
//                                                                .toString()
//                                                )
//                                        )),
                                        Query.of(q2 -> q2.bool(b1 -> b1
                                                .should(
                                                        nameQuery,
                                                        manufacturerQuery,
                                                        descriptionQuery
                                                )
                                        ))
                                )
                        )),
                Equipment.class
        );
        List<Hit<Equipment>> hits = searchResponse.hits().hits();
        return Flux.fromIterable(hits)
                .mapNotNull(Hit::source);
    }

}

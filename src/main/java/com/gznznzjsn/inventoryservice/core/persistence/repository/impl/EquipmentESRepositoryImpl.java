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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EquipmentESRepositoryImpl implements EquipmentESRepository {

    private final ElasticsearchClient client;

    @Value("${settings.elasticsearch.equipment.name.slop}")
    private int nameSlop;

    @Value("${settings.elasticsearch.equipment.name.max-expansions}")
    private int nameMaxExpansions;

    @Value("${settings.elasticsearch.equipment.manufacturer.slop}")
    private int manufacturerSlop;

    @Value("${settings.elasticsearch.equipment.manufacturer.max-expansions}")
    private int manufacturerMaxExpansions;

    @Value("${settings.elasticsearch.equipment.description.slop}")
    private int descriptionSlop;

    @Value("${settings.elasticsearch.equipment.description.max-expansions}")
    private int descriptionMaxExpansions;

    @Override
    @SneakyThrows
    public Flux<Equipment> getAutocomplete(
            final GetEquipmentAutocompleteQuery query
    ) {
        Query nameQuery = Query.of(q -> q
                .matchPhrasePrefix(p -> p
                        .field("equipment_name")
                        .query(query.query())
                        .slop(nameSlop)
                        .maxExpansions(nameMaxExpansions)
                )
        );
        Query manufacturerQuery = Query.of(q -> q.matchPhrasePrefix(
                p -> p.field("manufacturer")
                        .query(query.query())
                        .slop(manufacturerSlop)
                        .maxExpansions(manufacturerMaxExpansions)
        ));
        Query descriptionQuery = Query.of(q -> q.matchPhrasePrefix(
                p -> p.field("description")
                        .query(query.query())
                        .slop(descriptionSlop)
                        .maxExpansions(descriptionMaxExpansions)
        ));
        Query inventoryQuery = Query.of(qr -> qr.queryString(qs -> qs.query(
                "inventory_id.keyword:"
                + query.inventoryId()
        )));
        SearchResponse<Equipment>
                searchResponse = client.search(
                s -> s.index("equipment")
                        .from(0)
                        .size(3)
                        .query(q -> q.bool(b -> b.must(
                                inventoryQuery,
                                Query.of(qri -> qri.bool(
                                        bl -> bl.should(
                                                nameQuery,
                                                manufacturerQuery,
                                                descriptionQuery
                                        )
                                ))
                        ))),
                Equipment.class
        );
        List<Hit<Equipment>> hits = searchResponse.hits().hits();
        return Flux.fromIterable(hits).mapNotNull(Hit::source);
    }

}

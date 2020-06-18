package com.libqa.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libqa.elastic.domain.document.Coffee;
import io.micrometer.core.instrument.search.Search;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.Action;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author edell.lee
 * @version 2020-06-18 12:38
 * @implNote
 */
@Slf4j
@Component
public class ElasticsearchProvider implements CoffeeUseCase {

    private final RestHighLevelClient restHighLevelClient;

    public ElasticsearchProvider(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Mono<Void> addDocument(Coffee coffee) throws IOException {
        IndexRequest indexRequest = new IndexRequest("cafe", "coffee")
                .source("title", coffee.getTitle(),
                        "price", coffee.getPrice());

        return Mono.create(sink -> {
            ActionListener<IndexResponse> actionListener = new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    sink.success();
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("actionListener fail", e.getMessage());
                }
            };
            restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, actionListener);
        });
    }

    @Override
    public Flux<Coffee> searchTermQueryByTitle(String title) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("title", title));
        return getCoffeeFlux(searchSourceBuilder);
    }


    @Override
    public Flux<Coffee> searchMatchPhraseQueryByTitle(String title) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("title", title));
        return getCoffeeFlux(searchSourceBuilder);
    }

    @Override
    public Flux<Coffee> findAll() {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(QueryBuilders.typeQuery("coffee"));

        return getCoffeeFlux(searchSourceBuilder);
    }



    private Flux<Coffee> getCoffeeFlux(SearchSourceBuilder searchSourceBuilder) {
        SearchRequest searchRequest = new SearchRequest("cafe");
        searchRequest.source(searchSourceBuilder);

        return Flux.create(sink -> {
            ActionListener<SearchResponse> actionListener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse searchResponse) {
                    for (SearchHit hit : searchResponse.getHits()) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            Coffee coffee = objectMapper.readValue(hit.getSourceAsString(), Coffee.class);
                            sink.next(coffee);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    sink.complete();
                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, actionListener);
        });
    }

}

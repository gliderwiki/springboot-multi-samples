package com.libqa.elastic.service;

import com.google.gson.Gson;
import com.libqa.elastic.domain.document.User;
import com.libqa.elastic.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.Action;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author edell.lee
 * @version 2020-06-19 12:42
 * @implNote
 */
@Slf4j
@Component
public class UserElasticsearchProvider implements UserUseCase {

    private final RestHighLevelClient restHighLevelClient;


    public UserElasticsearchProvider(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    @Override
    public Mono<Void> index(String index, String type, String userName, String message) {
        Gson gson = GsonUtil.gson();
        User user = new User();
        user.setUser(userName);
        user.setMessage(message);
        user.setPostDate(new Date());

        IndexRequest indexRequest = new IndexRequest(index, type).source(gson.toJson(user), XContentType.JSON);
        return Mono.create(sink -> {

            ActionListener<IndexResponse> actionListener = new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    log.info("index success : "+indexResponse.toString());
                    sink.success();
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("index error ", e);
                    sink.error(e);
                }
            };

            restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, actionListener);
        });

    }

    @Override
    public Flux<User> matchAll(String index) {
        final Gson gson = GsonUtil.gson();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        return Flux.create((FluxSink<User> sink) -> {
            ActionListener<SearchResponse> actionListener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse searchResponse) {
                    searchResponse.getHits().forEach(item -> {
                        User user = gson.fromJson(item.getSourceAsString(), User.class);
                        sink.next(user);
                    });

                    sink.complete();
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("# matchAll error ", e);
                    sink.error(e);
                }
            };
            restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, actionListener);
        });
    }

    @Override
    public Mono<List<User>> matchAllSync(String index) {
        final Gson gson = GsonUtil.gson();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);

        List<User> resultList = new ArrayList<>();

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            searchResponse.getHits().forEach(item -> {
                User user = gson.fromJson(item.getSourceAsString(), User.class);
                resultList.add(user);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Mono.just(resultList);
    }

    @Override
    public Mono<User> getUser(String index, String type, String id) {
        final Gson gson = GsonUtil.gson();
        GetRequest getRequest = new GetRequest(index, type, id);
        return Mono.create(sink -> {
            ActionListener<GetResponse> actionListener = new ActionListener<GetResponse>() {
                @Override
                public void onResponse(GetResponse documentFields) {
                    User user = gson.fromJson(documentFields.getSourceAsString(), User.class);
                    sink.success(user);
                }

                @Override
                public void onFailure(Exception e) {
                    sink.error(e);
                }
            };

            restHighLevelClient.getAsync(getRequest, RequestOptions.DEFAULT, actionListener);
        });
    }
}

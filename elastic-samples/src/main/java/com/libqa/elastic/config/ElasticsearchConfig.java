package com.libqa.elastic.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticsearchConfig {

//    @Value("${elasticsearch.host}")
//    private String host;
//
//    @Value("${elasticsearch.port}")
//    private int port;


    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticsearchProperties elasticsearchProperties) {
//        return new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost(host, port, "http")
//                        // ,new HttpHost(host2,port,"http")
//
//                )
//        );

        return new RestHighLevelClient(
                RestClient.builder(elasticsearchProperties.hosts())
        );
    }


}
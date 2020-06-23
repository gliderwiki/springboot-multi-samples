package com.libqa.elastic.config;

import lombok.Setter;
import org.apache.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author edell.lee
 * @version 2020-06-18 17:12
 * @implNote
 */
@Component
@Setter
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {
    private List<String> hosts;

    public HttpHost[] hosts() {
        return hosts.stream().map(HttpHost::create).toArray(HttpHost[]::new);
    }
}

package com.example.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * es 配置文件
 * @author jun
 */
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    /**
     * 单机
     * @return
     */
    @Bean
    public RestHighLevelClient restClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        return client;
    }

    /**
     * 集群
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.99.100",9200, "http"),
                        new HttpHost("192.168.99.100",9201,"http")));
        return client;
    }

    /**
     * 方法二：继承Springboot依赖中提供的配置类
     * @return
     */
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.99.100:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}

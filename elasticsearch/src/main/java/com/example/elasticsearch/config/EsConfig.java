package com.example.elasticsearch.config;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * es config
 *
 * @author song
 * @date 2021/3/25
 */
@Slf4j
@Configuration
public class EsConfig  extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    String[] ipAddress;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ipAddress)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}

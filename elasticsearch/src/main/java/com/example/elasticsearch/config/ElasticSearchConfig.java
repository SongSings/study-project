package com.example.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * es 配置文件
 *
 * @author jun
 */
@Slf4j
@Configuration
public class ElasticSearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    String[] ipAddress;

    private static final int ADDRESS_LENGTH = 2;
    private static final String HTTP_SCHEME = "http";

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        Arrays.stream(ipAddress)
                                .map(this::makeHttpHost)
                                .filter(Objects::nonNull)
                                .toArray(HttpHost[]::new)
                )
        );
    }

    private HttpHost makeHttpHost(String s) {
        assert !StringUtils.isEmpty(s);
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            log.info(ip + ":" + port);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            return null;
        }
    }

}

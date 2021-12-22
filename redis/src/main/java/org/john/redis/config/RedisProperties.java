package org.john.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author john
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisProperties {

    public String keyPrefix;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
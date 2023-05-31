package org.john.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author john
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisProperties {

    public String keyPrefix;


    public Boolean useKeyPrefix;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Boolean getUseKeyPrefix() {
        return useKeyPrefix;
    }

    public void setUseKeyPrefix(boolean useKeyPrefix) {
        this.useKeyPrefix = useKeyPrefix;
    }

}
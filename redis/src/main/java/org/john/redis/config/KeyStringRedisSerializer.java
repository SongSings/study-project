package org.john.redis.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * @author john
 * @date 2021-12-21
 * @desc 字符串key序列化自定义处理
 */
public class KeyStringRedisSerializer implements RedisSerializer<String> {

    private String CACHE_PREFIX;

    private final Charset charset;

    public KeyStringRedisSerializer() {
        this.charset = Charset.forName("UTF8");
    }

    public KeyStringRedisSerializer(RedisProperties properties) {
        this(Charset.forName("UTF8"), properties);
    }

    public KeyStringRedisSerializer(Charset charset, RedisProperties properties) {
        this.charset = charset;
        CACHE_PREFIX = properties.getKeyPrefix();
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset).replaceFirst(CACHE_PREFIX, ""));
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        return (string == null ? null : (CACHE_PREFIX + string).getBytes(charset));
    }

}

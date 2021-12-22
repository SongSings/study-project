package org.john.redis.service;

import java.util.Map;

/**
 * @author john
 * date 2021-12-21
 */
public interface CacheService {

    String add(String key, String value);

    String get(String key);

    Object test(Map map);

    Object updTest(Map map);

    boolean delTest(Map map);

}

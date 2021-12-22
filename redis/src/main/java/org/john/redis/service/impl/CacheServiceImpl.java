package org.john.redis.service.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import org.john.redis.service.CacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author john
 * @date 2021-12-21
 * @desc
 */
@Service
public class CacheServiceImpl implements CacheService {

    public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(21);

    public static final String defaultKey = "default";

    public static List<User> userMaps = new ArrayList<>(21);

    @Override
    @CachePut(cacheNames = defaultKey, key = "#key")
    public String add(String key, String value) {
        map.put(key, value);
        return map.get(key);
    }

    @Override
    @Cacheable(value = defaultKey,key = "#key")
    public String get(String key) {
        return map.get(key);
    }


    @Override
    @Cacheable(cacheNames = {"test"}, key = "'user_'+#map.get('id')",unless="#result==null")
    public Object test(Map map) {
        System.out.println("测试query");
        return userMaps.stream().filter(x -> Objects.equals(x.getId(), map.get("id"))).collect(Collectors.toList());
    }

    @Override
    @CachePut(cacheNames = {"test"}, key = "'user_'+#map.get('id')",unless="#result==null")
    public Object updTest(Map map) {
        System.out.println("测试upd");

        List<User> users = userMaps.stream().filter(x -> Objects.equals(x.getId(), map.get("id"))).collect(Collectors.toList());

        users.forEach(x->{
            x.setName((String) map.get("name"));
        });
        if(users.size()==0){
            userMaps.add(new User().setId((String) map.get("id")).setName((String) map.get("name")));
        }

        return userMaps.stream().filter(x -> Objects.equals(x.getId(), map.get("id"))).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = "test", key = "'user_'+#map.get('id')",allEntries=false,beforeInvocation=false)
    public boolean delTest(Map map) {
        System.out.println("测试del");
        return userMaps.removeIf(x-> Objects.equals(x.getId(), map.get("id")));
    }

/*  @Cacheable
      将方法的运行结果进行缓存 @Cacheable
      以后再要相同的数据
      直接从缓存中获取
      就不用在执行方法了

      几个属性
              value/cacheNames 指定缓存的名字
              key 缓存数据使用的key，默认使用方法参数的值
              keyGenerator：key的生成器，可以自己指定key的生成器的组件id
                      key/keyGenerator： 二选一
              cacheManager：指定缓存管理器 或者cacheResolver指定缓存解析器  二选一
              condition：指定符合条件的情况下才缓存
              unless：否定缓存，当unless指定的条件是true，我们的方法返回值就不会被缓存 可以获取到结果进行判断
              sync：是否使用异步模式
   */

   /*
      @CachePut     缓存更新
      @CacheEvict   清除缓存
    */

    @Data
    @Accessors(chain = true)
    public static class User {

        private String id;

        private String name;
    }
}

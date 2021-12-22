package org.john.redis.controller;

import lombok.RequiredArgsConstructor;
import org.john.redis.service.CacheService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author songjun
 * @date 2021-12-21
 * @desc
 */
@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final CacheService cacheService;

    @PostMapping("add")
    public Object setKey(String key, String value){
        return cacheService.add(key,value);
    }

    @GetMapping("get_value")
    public String getValue(String key){
        return cacheService.get(key);
    }

    @GetMapping(value = "/test")
    public Object test(@RequestParam Map map){
        return cacheService.test(map);
    }


    @GetMapping(value = "/upd")
    public Object upd(@RequestParam Map map){
        return cacheService.updTest(map);
    }

    @GetMapping(value = "/del")
    public boolean del(@RequestParam Map map){
        return cacheService.delTest(map);
    }



}

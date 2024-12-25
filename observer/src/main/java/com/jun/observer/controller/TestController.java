package com.jun.observer.controller;

import com.alibaba.fastjson.JSONObject;
import com.jun.observer.domain.TestJsonIndex;
import com.jun.observer.mapper.TestJsonMapper;
import lombok.RequiredArgsConstructor;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author songjun
 * @description TODO
 * @since 2024/7/18
 */
@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class TestController {

    private final TestJsonMapper testJsonMapper;

    private volatile int i = 0;

    @PostMapping(value = {"/synchronization/user","/synchronization/update/user", "/synchronization/tenant"})
    public Map<String, Object> sync(HttpServletRequest request, @RequestBody HashMap map){
        Map<String, Object> test = test(request, map);
        test.put("data", true);
        return test;
    }


    @PostMapping(value = "test")
    public Map<String, Object> test(HttpServletRequest request, @RequestBody HashMap map){
        System.out.println("\t\n-------------start----------------");
        Enumeration<String> headerNames = request.getHeaderNames();
        HashMap<String, Object> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            System.out.println(key + ":" + request.getHeader(key));
            headerMap.put(key, request.getHeader(key));
        }
        System.out.println("-----------------------------" + i++);
        String json = JSONObject.toJSONString(map);
        System.out.println("结果 ： \n" + json);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);

        TestJsonIndex entity = new TestJsonIndex();
        entity.setHead(JSONObject.toJSONString(headerMap));
        entity.setBody(json);
        entity.setReturnBody(JSONObject.toJSONString(result));

        testJsonMapper.insert(entity);
        return result;
    }

    @PostMapping("save")
    public Map<String, Object> save(@RequestBody HashMap map){

        String jsonStr = JSONObject.toJSONString(map);
        TestJsonIndex json = new TestJsonIndex();
        // json.setId("4");
        json.setTitle(jsonStr);
        TestJsonIndex.JsonField jsonField = new TestJsonIndex.JsonField();
        jsonField.setSubField1("");
        jsonField.setSubField2("");
        jsonField.setSubField3(i++);
        json.setJsonField(jsonField);
        Integer insert = testJsonMapper.insert(json);
        System.out.println("insert = " + insert);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        return result;
    }

    @GetMapping("/test/search")
    public List<TestJsonIndex> search(String name){

        LambdaEsQueryWrapper<TestJsonIndex> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(name), TestJsonIndex::getTitle, name);
        return testJsonMapper.selectList(wrapper);
    }

    @GetMapping("/test/createIndex")
    public Boolean createIndex() {
        // 1.初始化-> 创建索引(相当于mysql中的表)
        return testJsonMapper.createIndex();
    }

    @DeleteMapping("test/delIndex")
    public void delIndex() {
        // 删除索引
        testJsonMapper.deleteIndex("test-json-index");
    }

    @DeleteMapping("test/del")
    public void del() {
        LambdaEsQueryWrapper<TestJsonIndex> wrapper = new LambdaEsQueryWrapper<>();
        // 删除全部数据
        testJsonMapper.delete(wrapper);
    }
}

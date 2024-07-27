package com.jun.observer.controller;

import com.alibaba.fastjson.JSONObject;
import com.jun.observer.domain.TestJsonIndex;
import com.jun.observer.mapper.TestJsonMapper;
import lombok.RequiredArgsConstructor;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
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
@RequiredArgsConstructor
public class TestController {

    private final TestJsonMapper testJsonMapper;

    private volatile int i = 0;

    @PostMapping(value = "test")
    public Map<String, Object> test(HttpServletRequest request, @RequestBody HashMap map){
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()){
//            String key = headerNames.nextElement();
//            System.out.println(key + ":" + request.getHeader(key));
//        }
        System.out.println("-----------------------------" + i++);
        String json = JSONObject.toJSONString(map);
        System.out.println("结果 ： \n" + json);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
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

    @GetMapping("list")
    public List<TestJsonIndex> list(){

        LambdaEsQueryWrapper<TestJsonIndex> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(TestJsonIndex::getTitle, "第一");
        return testJsonMapper.selectList(wrapper);
    }
}

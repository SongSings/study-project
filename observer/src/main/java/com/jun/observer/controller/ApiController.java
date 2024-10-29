package com.jun.observer.controller;

import com.alibaba.fastjson.JSONObject;
import com.jun.observer.domain.ApiTestIndex;
import com.jun.observer.mapper.ApiTestMapper;
import lombok.RequiredArgsConstructor;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author songjun
 * @description
 * @since 2024/7/23
 */
@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ApiTestMapper apiTestMapper;

    @GetMapping("/createIndex")
    public Boolean createIndex() {
        // 1.初始化-> 创建索引(相当于mysql中的表)
        return apiTestMapper.createIndex();
    }

    @PostMapping("/insert1")
    public Map<String, Object> insert1(@RequestBody HashMap map) {
        insert("V3-群发任务详情状态同步", map);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        return result;
    }

    @PostMapping("/insert2")
    public Map<String, Object> insert2(@RequestBody HashMap map) {
        insert("V3-群发消息回复同步", map);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        return result;
    }

    @PostMapping("/insert3")
    public Map<String, Object> insert3(@RequestBody HashMap map) {
        insert("V3-群发消息同步", map);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        return result;
    }

    private void insert(String name , HashMap map){
        // 2.初始化-> 新增数据
        ApiTestIndex document = new ApiTestIndex();

        String json = JSONObject.toJSONString(map);
        document.setParam(json);
        document.setName(name);
        apiTestMapper.insert(document);
    }

    @PostMapping("/insert")
    public  Map<String, Object> insert(@RequestBody HashMap map) {
        // 2.初始化-> 新增数据
        ApiTestIndex document = new ApiTestIndex();

        String json = JSONObject.toJSONString(map);
        document.setParam(json);
        document.setName("默认");
        apiTestMapper.insert(document);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        return result;
    }

    @GetMapping("/search")
    public List<ApiTestIndex> search(String name) {
        // 3.查询出所有标题为老汉的文档列表
        LambdaEsQueryWrapper<ApiTestIndex> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(name), ApiTestIndex::getName, name);
        wrapper.like(!StringUtils.hasText(name), ApiTestIndex::getName, "V3");
        return apiTestMapper.selectList(wrapper);
    }

    @DeleteMapping("del")
    public void del() {
        LambdaEsQueryWrapper<ApiTestIndex> wrapper = new LambdaEsQueryWrapper<>();
        // 删除全部数据
        apiTestMapper.delete(wrapper);
    }

    @DeleteMapping("delIndex")
    public void delIndex() {
        // 删除索引
        apiTestMapper.deleteIndex("api-test-index");
    }
}

package com.example.es;

import com.example.es.config.ElasticSearchUtil;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author songjun
 * @date 2021-03-24
 * @desc es方法测试类
 */

@Slf4j
@SpringBootTest
public class EsOpsTests {

    @Autowired
    ElasticSearchUtil esClient;

    private static final String source = "{\n" +
            "    \"properties\":{\n" +
            "        \"id\":{\n" +
            "            \"type\":\"long\"\n" +
            "        },\n" +
            "        \"username\":{\n" +
            "            \"type\":\"text\",\n" +
            "            \"fields\":{\n" +
            "                \"keyword\":{\n" +
            "                    \"type\":\"keyword\",\n" +
            "                    \"ignore_above\":256\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"create_time\":{\n" +
            "            \"type\":\"date\"\n" +
            "        },\n" +
            "        \"email\":{\n" +
            "            \"type\":\"text\",\n" +
            "            \"fields\":{\n" +
            "                \"keyword\":{\n" +
            "                    \"type\":\"keyword\",\n" +
            "                    \"ignore_above\":256\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";
    private static final String index = "record";

    /**
     * create index
     */
    @Test
    void createIndex() {
        boolean create_flag = esClient.createIndex(index, source);
        TestCase.assertEquals(create_flag, true);
    }

    /**
     * update index‘s mapping
     */
    @Test
    void putMapping() {
        boolean flag = esClient.putMappingRequest(index, source);
        TestCase.assertEquals(flag, true);
    }

    /**
     * 索引文档，即往索引里面放入文档数据
     */
    @Test
    void indexDocumentTests() {
        esClient.indexDocument(index);
    }

    /**
     * select document by id
     */
    @Test
    void getDocument() {
        esClient.getDocument(index, "1");
    }

    /**
     * Paging query
     */
    @Test
    void search() {
        esClient.search(index);
    }

    /**
     * 高亮查询
     */
    @Test
    void highlight() {
        esClient.highlight(index);
    }

    /**
     * 查询建议
     */
    @Test
    void termSuggest() {
        esClient.termSuggest(index);
    }

    @Test
    void aggregation() {
        esClient.aggregation(index);
    }

    /**
     * delete index
     */
    @Test
    void deleteIndex() {
        boolean deleteFlag = esClient.deleteIndex(index);
        TestCase.assertEquals(deleteFlag, true);
    }

    /**
     * delete document by id
     */
    @Test
    void deleteDocument() {
        esClient.deleteDocument(index, "1");
    }
    /**
     * todo add Cursor paging query
     */

}

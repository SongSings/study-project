package com.example.es;

import com.example.es.config.ElasticSearchUtil;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class EsOpsTests {

    @Autowired
    ElasticSearchUtil esClient;

    @Test
    void createIndex() {
        final boolean twitter = esClient.createIndex("twitter", "");
        TestCase.assertEquals(twitter,true);
    }

    @Test
    void putMapping() {
        boolean twitter = esClient.putMappingRequest("twitter");
        //Assert.assertEquals(twitter,true);
        TestCase.assertEquals(twitter,true);
    }

    @Test
    void indexDocumentTests(){
        esClient.indexDocument();
    }

    @Test
    void getDocument(){
        esClient.getDocument();
    }

    @Test
    void search(){
        esClient.search();
    }

    @Test
    void highlight(){
        esClient.highlight();
    }

    @Test
    void termSuggest(){
        esClient.termSuggest();
    }

    @Test
    void aggregation(){
        esClient.aggregation();
    }

}

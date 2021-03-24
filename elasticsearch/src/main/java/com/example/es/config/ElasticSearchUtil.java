package com.example.es.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jun
 * @see ：https://www.cnblogs.com/z00377750/p/13300196.html
 */
@Slf4j
@Component
public class ElasticSearchUtil {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 创建索引
     *
     * @param index  索引
     * @param source 索引的mapping
     * @return
     * @throws IOException
     */
    @SneakyThrows
    public boolean createIndex(String index, String source) {

        //创建索引
        CreateIndexRequest request = new CreateIndexRequest(index);

        // 2、设置索引的settings
        request.settings(Settings.builder()
                // 分片数
                .put("index.number_of_shards", 1)
                // 副本数
                .put("index.number_of_replicas", 1)
        );

        // 3、设置索引的mapping 默认文档类型_doc
        request.mapping(source, XContentType.JSON);

        // 4、 设置索引的别名
        request.alias(new Alias("User"));

        // 5、 发送请求
        // 5.1 同步方式发送请求
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

        // 6、处理响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        log.info("acknowledged = " + acknowledged);
        log.info("shardsAcknowledged = " + shardsAcknowledged);

        return acknowledged;

        // 5.1 异步方式发送请求
            /*ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
                @Override
                public void onResponse(
                        CreateIndexResponse createIndexResponse) {
                    // 6、处理响应
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    boolean shardsAcknowledged = createIndexResponse
                            .isShardsAcknowledged();
                   logger.info("acknowledged = " + acknowledged);
                   logger.info(
                            "shardsAcknowledged = " + shardsAcknowledged);
                }

                @Override
                public void onFailure(Exception e) {
                   logger.info("创建索引异常：" + e.getMessage());
                }
            };

            client.indices().createAsync(request, listener);
            */
    }

    /**
     * 自定义json字符串方式设置Mapping
     *
     * @throws IOException
     */
    @SneakyThrows
    public boolean putMappingRequest(String index, String source) {

        PutMappingRequest request = new PutMappingRequest(index);
        request.source(source, XContentType.JSON);

        boolean isAcknowledged = client.indices().putMapping(request, RequestOptions.DEFAULT).isAcknowledged();
        log.info("isAcknowledged = " + isAcknowledged);
        return isAcknowledged;
    }

    /**
     * 索引文档，即往索引里面放入文档数据.类似于数据库里面向表里面插入一行数据，一行数据就是一个文档
     *
     * @throws IOException
     */
    @SneakyThrows
    public void indexDocument(String index) {
        // 1、创建索引请求
        IndexRequest request = new IndexRequest(index);
        // 自定义id,不传即使用自动生成guid
        request.id("1");

        // 2、准备文档数据
        // 方式一：直接给JSON串
        String jsonString = "{" +
                "\"id\":1," +
                "\"username\":\"haha\"," +
                "\"email\":\"haha@outlook.com\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        // 方式二：以map对象来表示文档
        // Map<String, Object> jsonMap = new HashMap<>();
        // jsonMap.put("id", 2);
        // jsonMap.put("username", "王二");
        // jsonMap.put("email", "wang2@fox.com");
        // request.source(jsonMap);

        // 方式三：用XContentBuilder来构建文档
        // XContentBuilder builder = XContentFactory.jsonBuilder();
        // builder.startObject();
        // {
        //     builder.field("id", 3);
        //     builder.field("username", "张三");
        //     builder.field("email", "zhang3@fox.com");
        // }
        // builder.endObject();
        // request.source(builder);

        // 方式四：直接用key-value对给出
        // request.source("id", 4,
        //         "username", "李四",
        //         "email", "li4@fox.com",

        // 3、其他的一些可选设置
        // 设置routing值
        // request.routing("routing");
        // 设置主分片等待时长
        // request.timeout(TimeValue.timeValueSeconds(1));
        // 设置重刷新策略
        // request.setRefreshPolicy("wait_for");
        // 设置版本号
        // request.version(2);
        // 操作类别
        // request.opType(DocWriteRequest.OpType.CREATE);

        //4、发送请求
        IndexResponse indexResponse = null;
        try {
            // 同步方式
            indexResponse = client.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            // 捕获，并处理异常
            //判断是否版本冲突、create但文档已存在冲突
            if (e.status() == RestStatus.CONFLICT) {
                log.info("冲突了，请在此写冲突处理逻辑！" + e.getDetailedMessage());
            }
            log.info("索引异常");
        }

        //5、处理响应
        if (indexResponse != null) {
            String indexz = indexResponse.getIndex();
            String id = indexResponse.getId();
            log.info("id:{}", id);
            long version = indexResponse.getVersion();
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                log.info("新增文档成功，处理逻辑代码写到这里。");
            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                log.info("修改文档成功，处理逻辑代码写到这里。");
            }
            // 分片处理信息
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

            }
            // 如果有分片副本失败，可以获得失败原因信息
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    log.info("副本失败原因：" + reason);
                }
            }
        }
    }


    /**
     * 获取文档数据
     */
    @SneakyThrows
    public void getDocument(String index, String id) {
        // 1、创建获取文档请求
        GetRequest request = new GetRequest(index, id);

        // 2、可选的设置
        //request.routing("routing");
        //request.version(2);

        //request.fetchSourceContext(new FetchSourceContext(false)); //是否获取_source字段
        //选择返回的字段
        String[] includes = new String[]{"id", "username", "email"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        //也可写成这样
            /*String[] includes = Strings.EMPTY_ARRAY;
            String[] excludes = new String[]{"message"};
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);*/


        // 取stored字段
            /*request.storedFields("message");
            GetResponse getResponse = client.get(request);
            String message = getResponse.getField("message").getValue();*/


        //3、发送请求
        GetResponse getResponse = null;
        try {
            // 同步请求
            getResponse = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                log.info("没有找到该id的文档");
            }
            if (e.status() == RestStatus.CONFLICT) {
                log.info("获取时版本冲突了，请在此写冲突处理逻辑！");
            }
            log.info("获取文档异常" + e);
        }

        //4、处理响应
        if (getResponse != null) {
            index = getResponse.getIndex();
            String type = getResponse.getType();
            id = getResponse.getId();
            if (getResponse.isExists()) { // 文档存在
                long version = getResponse.getVersion();
                String sourceAsString = getResponse.getSourceAsString(); //结果取成 String
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();  // 结果取成Map
                byte[] sourceAsBytes = getResponse.getSourceAsBytes();    //结果取成字节数组
                log.info("index:" + index + "  type:" + type + "  id:" + id);
                log.info(sourceAsString);
            } else {
                log.info("没有找到该id的文档");
            }
        }

        //异步方式发送获取文档请求
            /*
            ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
                @Override
                public void onResponse(GetResponse getResponse) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            client.getAsync(request, listener);
            */
    }

    /**
     * 条件分页查询
     * @param index
     */
    @SneakyThrows
    public void search(String index) {
        // 1、创建search请求
        SearchRequest searchRequest = new SearchRequest(index);

        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 构造QueryBuilder
        /*QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("username", "JonssonYan")
                    // 对匹配查询启用模糊匹配
                   .fuzziness(Fuzziness.AUTO)
                   // 在匹配查询中设置前缀长度选项
                   .prefixLength(3)
                   // 设置最大扩展选项以控制查询的模糊过程
                   .maxExpansions(10);
           sourceBuilder.query(matchQueryBuilder);*/

        sourceBuilder.query(QueryBuilders.matchQuery("email", "haha@outlook.com"));
        // 设置from确定结果索引以开始搜索的选项。预设为0。
        sourceBuilder.from(0);
        // 设置size用于确定要返回的搜索命中次数的选项。默认为10
        sourceBuilder.size(10);
        // 设置一个可选的超时时间，以控制允许搜索的时间。
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //是否返回_source字段
        //sourceBuilder.fetchSource(false);

        //设置返回哪些字段
        /*String[] includeFields = new String[] {"username", "email", "innerObject.*"};
           String[] excludeFields = new String[] {"_type"};
           sourceBuilder.fetchSource(includeFields, excludeFields);*/

        //指定排序
        //sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //sourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));

        // 设置返回 profile
        //sourceBuilder.profile(true);

        //将请求体加入到请求中
        searchRequest.source(sourceBuilder);

        // 可选的设置
        //searchRequest.routing("routing");

        // 高亮设置


        /*HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("username");
        highlightTitle.highlighterType("text");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("email");
        highlightBuilder.field(highlightUser);
        sourceBuilder.highlighter(highlightBuilder);*/


        //加入聚合
        /*TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
                   .field("company.keyword");
           aggregation.subAggregation(AggregationBuilders.avg("average_age")
                   .field("age"));
           sourceBuilder.aggregation(aggregation);*/

        //做查询建议
        /*SuggestionBuilder termSuggestionBuilder =
                   SuggestBuilders.termSuggestion("user").text("kmichy");
               SuggestBuilder suggestBuilder = new SuggestBuilder();
               suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
           sourceBuilder.suggest(suggestBuilder);*/

        //3、发送请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        //4、处理响应
        //搜索结果状态信息
        RestStatus status = searchResponse.status();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();

        //分片搜索情况
        int totalShards = searchResponse.getTotalShards();
        int successfulShards = searchResponse.getSuccessfulShards();
        int failedShards = searchResponse.getFailedShards();
        for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
            // failures should be handled here
        }

        //处理搜索命中文档结果
        SearchHits hits = searchResponse.getHits();

        TotalHits totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit

            index = hit.getIndex();
            String id = hit.getId();
            float score = hit.getScore();

            //取_source字段值
            String sourceAsString = hit.getSourceAsString(); //取成json串
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
            //从map中取字段值
                /*
                String documentTitle = (String) sourceAsMap.get("title");
                List<Object> users = (List<Object>) sourceAsMap.get("user");
                Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
                */
            log.info("index:" + index + "  id:" + id);
            log.info(sourceAsString);

            //取高亮结果
                /*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight = highlightFields.get("title");
                Text[] fragments = highlight.fragments();
                String fragmentString = fragments[0].string();*/
        }

        // 获取聚合结果
            /*
            Aggregations aggregations = searchResponse.getAggregations();
            Terms byCompanyAggregation = aggregations.get("by_company");
            Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
            Avg averageAge = elasticBucket.getAggregations().get("average_age");
            double avg = averageAge.getValue();
            */

        // 获取建议结果
            /*Suggest suggest = searchResponse.getSuggest();
            TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
            for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                for (TermSuggestion.Entry.Option option : entry) {
                    String suggestText = option.getText().string();
                }
            }
            */

        //异步方式发送获查询请求
            /*
            ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse getResponse) {
                    //结果获取
                }

                @Override
                public void onFailure(Exception e) {
                    //失败处理
                }
            };
            client.searchAsync(searchRequest, listener);
            */
    }

    /**
     * 高亮
     */
    @SneakyThrows
    public void highlight(String index) {

        // 1、创建search请求
        SearchRequest searchRequest = new SearchRequest(index);

        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构造QueryBuilder
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("username", "haha");
        sourceBuilder.query(matchQueryBuilder);

        //分页设置
            /*sourceBuilder.from(0);
            sourceBuilder.size(5); ;*/


        // 高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false).field("username").field("email")
                .preTags("<strong>").postTags("</strong>");
        //不同字段可有不同设置，如不同标签
            /*HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
            highlightTitle.preTags("<strong>").postTags("</strong>");
            highlightBuilder.field(highlightTitle);
            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content");
            highlightContent.preTags("<b>").postTags("</b>");
            highlightBuilder.field(highlightContent).requireFieldMatch(false);*/

        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);

        //3、发送请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        //4、处理响应
        if (RestStatus.OK.equals(searchResponse.status())) {
            //处理搜索命中文档结果
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                index = hit.getIndex();
                String type = hit.getType();
                String id = hit.getId();
                float score = hit.getScore();

                //取_source字段值
                //String sourceAsString = hit.getSourceAsString(); //取成json串
                Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
                //从map中取字段值
                    /*String title = (String) sourceAsMap.get("title");
                    String content  = (String) sourceAsMap.get("content"); */
                log.info("index:" + index + "  type:" + type + "  id:" + id);
                log.info("sourceMap : " + sourceAsMap);
                //取高亮结果
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight = highlightFields.get("username");
                if (highlight != null) {
                    Text[] fragments = highlight.fragments();  //多值的字段会有多个值
                    if (fragments != null) {
                        String fragmentString = fragments[0].string();
                        log.info("username highlight : " + fragmentString);
                        //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
                        //sourceAsMap.put("title", fragmentString);
                    }
                }

                highlight = highlightFields.get("email");
                if (highlight != null) {
                    Text[] fragments = highlight.fragments();  //多值的字段会有多个值
                    if (fragments != null) {
                        String fragmentString = fragments[0].string();
                        log.info("email highlight : " + fragmentString);
                        //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
                        //sourceAsMap.put("content", fragmentString);
                    }
                }
            }
        }

    }

    /**
     * 查询建议
     * 词项建议拼写检查，检查用户的拼写是否错误，如果有错给用户推荐正确的词，appel->apple
     */
    @SneakyThrows
    public void termSuggest(String index) {
        // 1、创建search请求
        //SearchRequest searchRequest = new SearchRequest();
        SearchRequest searchRequest = new SearchRequest(index);

        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.size(0);

        //做查询建议
        //词项建议
        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("username").text("haga");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
        sourceBuilder.suggest(suggestBuilder);

        searchRequest.source(sourceBuilder);
        //3、发送请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        //4、处理响应
        //搜索结果状态信息
        if (RestStatus.OK.equals(searchResponse.status())) {
            // 获取建议结果
            Suggest suggest = searchResponse.getSuggest();
            TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
            for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                log.info("text: " + entry.getText().string());
                for (TermSuggestion.Entry.Option option : entry) {
                    String suggestText = option.getText().string();
                    log.info("   suggest option : " + suggestText);
                }
            }
        }

    }

    @SneakyThrows
    public void aggregation(String index) {

        // 1、创建search请求
        //SearchRequest searchRequest = new SearchRequest();
        SearchRequest searchRequest = new SearchRequest(index);

        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.size(0);

        //加入聚合
        //字段值项分组聚合
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_age")
                .field("age").order(BucketOrder.aggregation("average_balance", true));
        //计算每组的平均balance指标
        aggregation.subAggregation(AggregationBuilders.avg("average_balance")
                .field("balance"));
        sourceBuilder.aggregation(aggregation);

        searchRequest.source(sourceBuilder);

        //3、发送请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        //4、处理响应
        //搜索结果状态信息
        if (RestStatus.OK.equals(searchResponse.status())) {
            // 获取聚合结果
            Aggregations aggregations = searchResponse.getAggregations();
            Terms byAgeAggregation = aggregations.get("by_age");
            log.info("aggregation by_age 结果");
            log.info("docCountError: " + byAgeAggregation.getDocCountError());
            log.info("sumOfOtherDocCounts: " + byAgeAggregation.getSumOfOtherDocCounts());
            log.info("------------------------------------");
            for (Terms.Bucket buck : byAgeAggregation.getBuckets()) {
                log.info("key: " + buck.getKeyAsNumber());
                log.info("docCount: " + buck.getDocCount());
                log.info("docCountError: " + buck.getDocCountError());
                //取子聚合
                Avg averageBalance = buck.getAggregations().get("average_balance");

                log.info("average_balance: " + averageBalance.getValue());
                log.info("------------------------------------");
            }
            //直接用key 来去分组
                /*Bucket elasticBucket = byCompanyAggregation.getBucketByKey("24");
                Avg averageAge = elasticBucket.getAggregations().get("average_age");
                double avg = averageAge.getValue();*/
        }


    }

    /**
     * 索引是否存在
     * @param indexName
     * @return
     */
    public boolean exists(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除索引
     * @param index
     * @return
     */
    public boolean deleteIndex(String index){
        boolean exists = exists(index);
        if(!exists) {
            //不存在就结束
            return false;
        }
        //索引存在，就执行删除
        long s = System.currentTimeMillis();
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        AcknowledgedResponse delete = new AcknowledgedResponse(false);
        try {
            delete = client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long t = System.currentTimeMillis();
        //计算删除耗时
        System.out.println(t-s);
        return delete.isAcknowledged();
    }

    /**
     * 删除文档
     * @param index
     * @param id
     * @return
     */
    @SneakyThrows
    public void deleteDocument(String index, String id){
        boolean exists = exists(index);
        if(!exists) {
            //不存在就结束
            return;
        }
        //索引存在，就执行删除
        DeleteRequest request = new DeleteRequest(index,id);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse);
    }
}
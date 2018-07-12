package com.test.es;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.cluster.storedscripts.*;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.index.reindex.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.StoredScriptSource;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TestES {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        Properties props = loadProp("set.properties");
        System.out.println(props);

        Settings settings = buildSettings(propsToMap(props));
        TransportClient client = new PreBuiltTransportClient(settings);
        Properties addr = loadProp("addresses.properties");
        Map<String, Integer> addressPortMap = buildAddressPortMap(propsToMap(addr));


        for (Map.Entry<String, Integer> entry : addressPortMap.entrySet()) {
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(entry.getKey()), entry.getValue()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();

        // 创建一个script到es中
//        PutStoredScriptRequestBuilder scriptRequestBuilder = PutStoredScriptAction.INSTANCE.newRequestBuilder(client);
//        PutStoredScriptResponse scriptResponse = scriptRequestBuilder
//                .setContent(new BytesArray("{\"script\":{\"lang\": \"painless\",\"code\": \"ctx._source.aa=params.a\"}}"), XContentType.JSON)
//                .setId("new_script").get();
//        System.out.println(scriptResponse.toString());
        //查询一个script
//        GetStoredScriptRequestBuilder scriptRequestBuilder1 = GetStoredScriptAction.INSTANCE.newRequestBuilder(client);
//        scriptRequestBuilder1.setId("new_script1");
//        GetStoredScriptResponse scriptResponse = scriptRequestBuilder1.get();
//        System.out.println(scriptResponse);
//        StoredScriptSource source = scriptResponse.getSource();
//        System.out.println(source); // 当脚本不存在的时候为null
//        System.out.println(source.toString());
//        DeleteStoredScriptRequestBuilder scriptRequestBuilder = DeleteStoredScriptAction.INSTANCE.newRequestBuilder(client);
//        DeleteStoredScriptResponse deleteStoredScriptResponse = scriptRequestBuilder.setId("new_script").get();
//        System.out.println(deleteStoredScriptResponse.isAcknowledged());


        //普通的update
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.index("kks").type("a").id("1");
//        updateRequest.doc(jsonBuilder.startObject().field("bb", 56).endObject());
//        UpdateResponse updateResponse = client.update(updateRequest).get();
//        System.out.println(updateResponse.toString());


        // terms
//        List<String> ids = new ArrayList<>();
//        ids.add("1");
//        ids.add("2");
//        TermsQueryBuilder query = QueryBuilders.termsQuery("_id", ids);
//        SearchRequestBuilder builder = client.prepareSearch("xiaogu");
//        SearchResponse searchResponse = builder.setQuery(query).get();
//        SearchHits hits = searchResponse.getHits();
//        for (SearchHit searchHitFields : hits.getHits()) {
//            Map<String, Object> source = searchHitFields.getSource();
//            System.out.println(searchHitFields.getId());
//            System.out.println(source);
//        }


        // prepareUpdate
//        UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate();
//        updateRequestBuilder.setIndex("aa").setType("a").setId("1");
//        updateRequestBuilder.setDoc("{\"dd\": 90}", XContentType.JSON);
////        updateRequestBuilder.setVersion(3); // 可以设置version，只有当version等于es中这个文档的version的时候才能成功
//        UpdateResponse updateResponse1 = updateRequestBuilder.get();
//        System.out.println(updateResponse1);
        // test script call times
//        for (int i = 0; i < 100; i++) {
//            Map<String, Object> params = new HashMap<>();
//            params.put("a", i);
//            updateRequestBuilder.setScript(new Script("ctx._source.aa=" + i));
////            updateRequestBuilder.setScript(new Script(ScriptType.STORED, "painless", "aa", params));
//            UpdateResponse updateResponse = updateRequestBuilder.get();
//            System.out.println(updateResponse);
//        }




        //
//        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("cluetest", "clue", "8bfef31e-0c78-11e8-b385-00163e0200fa");
//        try {
//            IndexResponse indexResponse = indexRequestBuilder.setSource("{\n" +
//                    "\t\t\t\t\"clue_category\": \"yiche_qiugou_clue\",\n" +
//                    "\t\t\t\t\"date_delete\": null,\n" +
//                    "\t\t\t\t\"date_update\": \"2018-02-05 13:30:08\",\n" +
//                    "\t\t\t\t\"city_code\": \"\",\n" +
//                    "\t\t\t\t\"sc_car_id\": \"\",\n" +
//                    "\t\t\t\t\"media\": \"\",\n" +
//                    "\t\t\t\t\"platform\": \"yiche\",\n" +
//                    "\t\t\t\t\"tp_dealer_id\": \"v168584\",\n" +
//                    "\t\t\t\t\"handphone\": \"15257617788\",\n" +
//                    "\t\t\t\t\"general_category\": \"\",\n" +
//                    "\t\t\t\t\"clue_id\": \"\",\n" +
//                    "\t\t\t\t\"id\": \"8bfef31e-0c78-11e8-b385-00163e0200fa\",\n" +
//                    "\t\t\t\t\"tag\": \"\",\n" +
//                    "\t\t\t\t\"phone_exists_in180\": 0,\n" +
//                    "\t\t\t\t\"sc_user_id\": \"\",\n" +
//                    "\t\t\t\t\"store_id\": \"000200\",\n" +
////                    "\t\t\t\t\"ext\": \"{\\\"type\\\": \\\"求购\\\", \\\"description\\\": \\\"求购 奥迪Q5 3-5年 20-30万\\\", \\\"car_city_code\\\": \\\"01055\\\", \\\"car_city_name\\\": \\\"台州\\\", \\\"intent_price_low\\\": 200000, \\\"car_province_code\\\": \\\"00974\\\", \\\"car_province_name\\\": \\\"浙江\\\", \\\"intent_max_mileage\\\": 0, \\\"intent_min_mileage\\\": 0, \\\"intent_price_height\\\": 300000}\",\n" +
//                    "\t\t\t\t\"area\": \"\",\n" +
//                    "\t\t\t\t\"business_category\": \"\",\n" +
//                    "\t\t\t\t\"clue_distribute_time\": null,\n" +
//                    "\t\t\t\t\"test\": \"\",\n" +
//                    "\t\t\t\t\"business_sub_category\": \"\",\n" +
//                    "\t\t\t\t\"source_text\": \"\",\n" +
//                    "\t\t\t\t\"clue_date_create\": \"2016-06-18 16:02:00\",\n" +
//                    "\t\t\t\t\"spm\": \"\",\n" +
//                    "\t\t\t\t\"operate\": \"add\",\n" +
//                    "\t\t\t\t\"system\": \"\",\n" +
//                    "\t\t\t\t\"date_create\": \"2016-06-23 11:45:12\",\n" +
//                    "\t\t\t\t\"tp_car_id\": \"\",\n" +
//                    "\t\t\t\t\"c_name\": \"钟先生(台州)\",\n" +
//                    "\t\t\t\t\"seq_id\": 53564,\n" +
//                    "\t\t\t\t\"status\": 1\n" +
//                    "\t\t\t}").get();
//            System.out.println(indexResponse);
//        }  catch (Exception e) {
//            System.out.println("dffdfdfdf: " + e);
//        }
        // 这是插入操作，也是全量更新操作，会将老的document设置成删除，然后重新插入一个document
//        IndexResponse response = client.prepareIndex("aa", "a", "1").setSource("{\"field5\": 22}", XContentType.JSON).get();
//        System.out.println(response.toString());


        //删除文档中的某个key
//        UpdateResponse updateResponse = client.prepareUpdate("ww", "w", "1").setScript(new Script("ctx._source.remove('aa.dd')")).get();
//        System.out.println(updateResponse.getResult().toString());


//        GetRequestBuilder getRequestBuilder = client.prepareGet("mm", "m", "1");
//        GetResponse getFields = getRequestBuilder.get();
//        Map<String, Object> source = getFields.getSource();
////        GetField esname = getFields.getField("esname");
//        System.out.println(getFields.getSource().get("id").getClass());
//        List l = (List) null;
//        System.out.println(l);
//        System.out.println(getRequestBuilder.toString()); // 没有json格式输出


        // 判断index是否处在
//        IndicesAdminClient indices = client.admin().indices();
//        IndicesExistsResponse response = indices.prepareExists("aa").get();
//        System.out.println(response.isExists());
//
//        // setTypes可以设置多个type，只要其中有一个不存在，则isExists是false
//        TypesExistsResponse response1 = indices.prepareTypesExists("shenfl").setTypes("ttt", "ff").get();
//        System.out.println(response1.isExists());


        //创建索引的时候改变stringmapping成keyword
//        CreateIndexRequestBuilder create = indices.prepareCreate("abcd");
//                create.setSettings(Settings.builder()
//                .put("index.number_of_shards", 2).put("index.number_of_replicas", 1));
//                create.addMapping("aa", "{\n" +
//                        "\t\t      \"dynamic_templates\": [\n" +
//                        "\t\t        {\n" +
//                        "\t\t          \"strings\": {\n" +
//                        "\t\t            \"match_mapping_type\": \"string\",\n" +
//                        "\t\t            \"mapping\": {\n" +
//                        "\t\t              \"type\": \"keyword\"\n" +
//                        "\t\t            }\n" +
//                        "\t\t          }\n" +
//                        "\t\t        }\n" +
//                        "\t\t      ]\n" +
//                        "\t\t    }", XContentType.JSON);
//                create.get();

//        CreateIndexRequestBuilder createIndexRequestBuilder = indices.prepareCreate("abc").setSettings(Settings.builder()
//                .put("index.number_of_shards", 2).put("index.number_of_replicas", 0));
//        createIndexRequestBuilder.addMapping("def", "{\n" +
//                "      \"properties\": {\n" +
//                "        \"field1\": {\n" +
//                "          \"type\": \"integer\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }", XContentType.JSON);
//        System.out.println(createIndexRequestBuilder.get().isShardsAcked());



        //bulk操作，可以同时操作多个index
//        BulkRequestBuilder bulk = client.prepareBulk();
//        IndexRequestBuilder add = client.prepareIndex("aa", "a", "1");
//        add.setSource("{\"22\":\"22\"}");
//        bulk.add(add);
//        add = client.prepareIndex("bb", "BB", "1");
//        add.setSource("{\"22\":\"22\"}");
//        bulk.add(add);
//        UpdateRequestBuilder update = client.prepareUpdate("aa", "a", "1").setDoc("{\"hh\": 99}"); // 这个update不会删除原来老的key
//        bulk.add(update);
//        BulkResponse responses = bulk.execute().actionGet();
//        Iterator<BulkItemResponse> iterator = responses.iterator();
//        if (iterator.hasNext()) {
//            BulkItemResponse next = iterator.next();
//            System.out.println(next.isFailed());
//            System.out.println(next.getFailureMessage());
//        }



        // 测试bulk的速度
//        long count = 194355;
//        while (true) {
//            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
//            for (int i = 0; i < 1; i++) {
//                IndexRequestBuilder index = client.prepareIndex("test", "it", String.valueOf(count));
//                index.setSource("{\n" +
//                        "                \"text\": \"hello world\",\n" +
//                        "                \"age\": " + count + ",\n" +
//                        "                \"name\": {\n" +
//                        "                        \"first\": \"jack chen\",\n" +
//                        "                        \"last\": \"tom\"\n" +
//                        "                }\n" +
//                        "        }");
//                bulkRequestBuilder.add(index);
//                count++;
//            }
//            BulkResponse bulkItemResponses = bulkRequestBuilder.execute().actionGet();
//            System.out.println("one batch");
//        }


        // 测试插入速度
//        IndexRequestBuilder builder = null;
//        for (int i = 0; i < 1000000000; i++) {
//            builder = client.prepareIndex("sss", "aa", String.valueOf(i));
//            builder.setSource("{\n" +
//                    "\t\"id\": 1,\n" +
//                    "\t\"name\": \"上课减肥咖啡了公司看看\"\n" +
//                    "}", XContentType.JSON);
//            DocWriteResponse.Result result = builder.get().getResult();
////            System.out.println(result.getLowercase());
//            if (i % 100 == 0) System.out.println(i);
//        }



        // test visible
//        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("aa", "a", "13");
//        String s = "{\"aa\": 13}";
//        IndexResponse response = indexRequestBuilder.setSource(s, XContentType.JSON).get();
//        System.out.println(response);
//        Thread.sleep(5000);
//
//        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("aa");
//        TermQueryBuilder termQuery = QueryBuilders.termQuery("aa", 13);
//        SearchResponse response1 = searchRequestBuilder.setQuery(termQuery).setSize(5).get();
//        System.out.println(response1.getHits().getTotalHits());
//        System.out.println(searchRequestBuilder);




        // test scroll operation
//        SearchRequestBuilder searchRequestBuilder = client.prepareSearch();
//        searchRequestBuilder.setIndices("clue_v1");
//        searchRequestBuilder.setTypes("clue_type");
//        searchRequestBuilder.setScroll(new TimeValue(60000));
//        SearchResponse searchResponse = searchRequestBuilder.get();
//        String scrollId = searchResponse.getScrollId();
//        System.out.println(scrollId);
//        // 第一次也会返回10条数据
//        SearchHit[] searchHits = searchResponse.getHits().getHits();
//        for (SearchHit searchHit : searchHits) {
//            String source = searchHit.getSource().toString();
//            System.out.println("--------- searchByScroll source {}" + source);
//        }
//
//        System.out.println("----------------------------------");
//        while (true) {
//            SearchScrollRequestBuilder scrollRequestBuilder = client.prepareSearchScroll(scrollId);
//            scrollRequestBuilder.setScroll(new TimeValue(60000));
//            SearchResponse response = scrollRequestBuilder.get();
//            if (response.getHits().getHits().length == 0) {
//                break;
//            }
//            SearchHit[] hits = response.getHits().getHits();
//            for (SearchHit searchHit : hits) {
//                String source = searchHit.getSource().toString();
//                System.out.println("--------- searchByScroll source {}" + source);
//            }
//        }
//        System.out.println("ending...");




        // update by query
//        UpdateByQueryRequestBuilder updateByQueryRequestBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
//        Script script = new Script("ctx._source.name=ctx._source.name+'呵呵'"); //  一定要引号引起来
//        updateByQueryRequestBuilder.source("aaa").script(script)
//                .filter(QueryBuilders.termQuery("id", 1))
//                .abortOnVersionConflict(false).get();
//        System.out.println("over...");



        // re score
//        Script script = new Script("");
//        ScriptScoreFunctionBuilder functionBuilder = ScoreFunctionBuilders.scriptFunction(script);
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(functionBuilder);
//        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("aa");
//        searchRequestBuilder.setQuery(functionScoreQueryBuilder);
//        String s = searchRequestBuilder.get().toString();
//        System.out.println(s);


        // reindex
//        ReindexRequestBuilder reindexRequestBuilder = ReindexAction.INSTANCE.newRequestBuilder(client);
//        reindexRequestBuilder.source("testdsl").destination("test_02");
//        BulkByScrollResponse bulkByScrollResponse = reindexRequestBuilder.get(TimeValue.timeValueHours(1));
//
//
//        System.out.println("over");



        // test SearchRequestBuilder toString
//        SearchRequestBuilder builder = client.prepareSearch("scripscore_1");
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("age", 11);
//        boolQueryBuilder.must(termQueryBuilder);
//        builder.setQuery(boolQueryBuilder);
//        System.out.println(builder.toString());
//        SearchResponse searchResponse = builder.get();
//        System.out.println(searchResponse.toString());




        // 测试数组插入
//        for (int i = 1; i <= 5000; i++) {
//            IndexRequestBuilder indexRequestBuilder = client.prepareIndex("aa", "a", String.valueOf(i));
//            String s = "{\"aa\": " + generateList().toString() + ", \"bb\": 22}";
//            System.out.println(s.length());
//            IndexResponse response = indexRequestBuilder.setSource(s, XContentType.JSON).get();
//            System.out.println(response.toString());
//        }
        // 测试数组更新
//        long start = System.currentTimeMillis();
//        for (int i = 1; i <= 5000; i++) {
//            UpdateRequestBuilder requestBuilder = client.prepareUpdate("aa", "a", String.valueOf(i));
//            UpdateResponse updateResponse = requestBuilder.setDoc("{\"aa\": \"43\"}", XContentType.JSON).get();
//            System.out.println(updateResponse.toString());
//        }
//        System.out.println("cost: " + (System.currentTimeMillis() - start));


        client.close();

    }

    public static List<String> generateList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            UUID uuid = UUID.randomUUID();
            String[] split = uuid.toString().split("-");
            for (String s : split) {
                list.add("\"" + s + "\"");
            }
        }
        return list;
    }

    public static Properties loadProp(String file) throws IOException {
        ClassLoader classLoader = TestES.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(file);
        Properties props = new Properties();
        props.load(new InputStreamReader(stream, "UTF-8"));
        return props;
    }

    public static Map<String, String> propsToMap(Properties properties) {
        Map<String, String> map = null;
        Set<Map.Entry<Object, Object>> set = properties.entrySet();
        if (set != null && set.size() > 0) {
            map = new HashMap<String, String>();
            for (Map.Entry<Object, Object> entry : set) {
                map.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return map;
    }

    public static Settings buildSettings(Map<String, String> settings) {
        Settings.Builder builder = Settings.builder();
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            if (entry.getValue().equalsIgnoreCase("false")) {
                builder.put(entry.getKey(), Boolean.parseBoolean(entry.getKey()));
            }
            if (entry.getValue().equalsIgnoreCase("true")) {
                builder.put(entry.getKey(), Boolean.parseBoolean(entry.getKey()));
            }
            builder.put(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    public static Map<String, Integer> buildAddressPortMap(Map<String, String> addressAndPort) {
        Map<String, Integer> addresses = new HashMap<String, Integer>();
        for (Map.Entry<String, String> entry : addressAndPort.entrySet()) {
            String val = entry.getValue();
            String[] addrAndPort = val.split(":");
            if (addrAndPort != null && addrAndPort.length == 2) {
                addresses.put(addrAndPort[0], Integer.parseInt(addrAndPort[1]));
            }
        }
        return addresses;
    }

}

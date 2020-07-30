package com.test.es;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class TestRestHighClient {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
//        testCreate(client);
        testSearch(client);
        client.close();
    }

    private static void testSearch(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchSourceBuilder.size(3);

        SearchRequest searchRequest = new SearchRequest()
                .indices("aa")
                .source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceRef().utf8ToString());
            Object object = JSON.parseObject(hit.getSourceRef().streamInput(), A.class);
            System.out.println(object);
        }
    }
    static class A {
        private String aa;

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        @Override
        public String toString() {
            return "A{" +
                    "aa='" + aa + '\'' +
                    '}';
        }
    }

    private static void testCreate(RestHighLevelClient client) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter_two");//创建索引
        //创建的每个索引都可以有与之关联的特定设置。
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 2)
//        );
        //创建索引时创建文档类型映射
        request.mapping(
                "{\n" +
                        "\t\"tweet\": {\n" +
                        "\t\t\"properties\": {\n" +
                        "\t\t\t\"message\": {\n" +
                        "\t\t\t\t\"type\": \"keyword\"\n" +
                        "\t\t\t}\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "}",//类型映射，需要的是一个JSON字符串
                XContentType.JSON);

        //为索引设置一个别名
//        request.alias(
//                new Alias("twitter_alias")
//        );
        //request.timeout("2m");//超时,等待所有节点被确认(使用字符串方式)

        //同步执行
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        //异步执行
        System.out.println(createIndexResponse.isAcknowledged());
        System.out.println(createIndexResponse.isShardsAcknowledged());
    }
}

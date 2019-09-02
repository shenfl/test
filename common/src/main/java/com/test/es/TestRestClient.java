package com.test.es;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;
import java.util.Collections;

public class TestRestClient {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("172.17.40.129", 9200))
                .build();
        test(restClient);
        test1(restClient);
    }

    private static void test1(RestClient restClient) throws IOException {
        HttpEntity entity = new NStringEntity("",
                ContentType.APPLICATION_JSON);
        Request request = new Request("GET", "/index_test/_search");
        request.setEntity(entity);
        Response response = restClient.performRequest(request);
        System.out.println(response.getEntity());
    }

    private static void test(RestClient restClient) throws IOException {
        HttpEntity entity = new NStringEntity("{\n\"book_id\":\"0001\",\n\"name\":\"Alice in Wonderland\"\n}",
                ContentType.APPLICATION_JSON);
        Request request = new Request("PUT", "/index_test/book/0001");
        request.setEntity(entity);
        Response response = restClient.performRequest(request);
        System.out.println(response.getEntity());

        //search a document 检索ES数据
        request = new Request("GET", "/index_test/_search");
        request.addParameter("pretty", "true");
        response = restClient.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}

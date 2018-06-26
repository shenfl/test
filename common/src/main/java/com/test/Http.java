package com.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

public class Http {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.custom().build();

//        HttpGet get = new HttpGet("http://local.dasouche-inc.net:8080/monitor/cluster/status");

//        HttpPost post = new HttpPost("http://ng-searcher.dasouche-inc.net/inspect/load");
////        HttpPost post = new HttpPost("http://local.dasouche-inc.net:8080/inspect/list");
//        post.addHeader("Content-type", "application/json; charset=utf-8");
//        StringEntity entity = new StringEntity("{\"application\": \"finance-car-lease\", \"id\": \"909593\"}", Charset.forName("UTF-8"));
//        post.setEntity(entity);

        HttpPost post = new HttpPost("http://ng-searcher.dasouche-inc.net/inspect/load");
//        HttpPost post = new HttpPost("http://local.dasouche-inc.net:8080/inspect/list");
        post.addHeader("Content-type", "application/json; charset=utf-8");
        StringEntity entity = new StringEntity("{\"application\": \"finance-car-lease\", \"id\": \"909593\"}", Charset.forName("UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse execute = client.execute(post);
        String s = EntityUtils.toString(execute.getEntity());
        System.out.println(s);

    }
}

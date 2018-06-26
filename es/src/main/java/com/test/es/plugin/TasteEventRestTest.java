//package com.test.es.plugin;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.elasticsearch.common.network.NetworkAddress;
//import org.elasticsearch.common.xcontent.XContentType;
//
//import java.net.InetSocketAddress;
//
///**
// * Created by shenfl on 2018/5/7
// */
//public class TasteEventRestTest extends TastePluginTest {
//    public void test_recommended_items_from_user() throws Exception {
//        final String index = "blog";
//        XContentType type = randomFrom(XContentType.values());
//        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
//
//            //下面这个语句代表从集群中获取各个节点的ip地址，不过目前集群就开启了一个节点，也就endpoint[0]有值
//            //如果集群中有多个节点，采用随机化测试，可用
//            //InetSocketAddress endpoint =randomFrom(cluster().httpAddresses());来从节点集群中随机获取一个节点的ip地址
//            InetSocketAddress[] endpoint = cluster().httpAddresses();
//
//            //得到url路径，生成get请求
//            this.restBaseUrl = "http://" + NetworkAddress.format(endpoint[0]);
//            HttpGet get=new HttpGet(restBaseUrl + "/"+index+"/_taste/event");
//
//            HttpResponse response = httpClient.execute(get);
//            System.out.println("post请求已发送");
//
//            JSONObject object = JSON.parseObject(response.getEntity().getContent(), JSONObject.class);
//            System.out.println(object.toJSONString());
//
//            //最后应该用一个断言来判断测试是否成功，不过并不知道怎么判断
//            //assertEquals("{\"acknowledged\":true}", response );
//
//        }
//
//    }
//}
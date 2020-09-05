package com.test.graph;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

/**
 * 阿里云gdb官网代码：https://help.aliyun.com/document_detail/107477.html?spm=a2c4g.11186623.6.591.7e2e24c02vgpRf
 * @author shenfl
 */
public class TestGDB {
    public static void main(String[] args) {
        try {
            String yaml = "/Users/shenfl/IdeaProjects/test/common_second/src/main/resources/gdb.yaml";
            Cluster cluster = Cluster.build(new File(yaml)).create();
            Client client = cluster.connect().init();
            String dsl = "g.addV(yourlabel).property(propertyKey, propertyValue)";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("yourlabel","area");
            parameters.put("propertyKey","wherence");
            parameters.put("propertyValue","shenzheng");
            ResultSet results = client.submit(dsl,parameters);
            List<Result> result = results.all().join();
            result.forEach(p -> System.out.println(p.getObject()));
            cluster.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

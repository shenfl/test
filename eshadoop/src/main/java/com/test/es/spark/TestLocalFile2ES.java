//package com.test.es.spark;
//
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
//import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;
//
//public class TestLocalFile2ES {
//    public static void main(String[] args) {
//        SparkSession spark=SparkSession.builder().appName("Sql2Es").config("es.nodes","172.17.40.129")
//                .config("es.port","9200").master("local[3]").getOrCreate();
//        Dataset<Row> person=spark.read().json("/Users/shenfl/IdeaProjects/test/eshadoop/src/main/resources/people.json");
//        person.show();
//        JavaEsSparkSQL.saveToEs(person,"test/person");
//    }
//}

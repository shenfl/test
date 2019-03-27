//package com.test.es.spark;
//
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
//import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;
///**
// * 会遇到Exception in thread "main" java.lang.SecurityException: Invalid signature file digest for Manifest main attributes
// * 只要在pom中加
// * <exclude>META-INF/*.SF</exclude>
// * <exclude>META-INF/*.DSA</exclude>
// * <exclude>META-INF/*.RSA</exclude>
// */
//public class TestHive2ES {
//    public static void main(String[] args) {
//        SparkSession spark = SparkSession
//                .builder()
//                .appName("Java Spark Hive Example")
//                .config("es.nodes","172.17.40.129")
//                .config("es.port","9200")
//                .enableHiveSupport()
//                .getOrCreate();
//        Dataset<Row> sql = spark.sql("select * from records");
////        sql.show();
//
//        JavaEsSparkSQL.saveToEs(sql,"test/person");
//    }
//}

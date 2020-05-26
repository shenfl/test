package com.test.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

public class Main {
    public static void main(String[] args) {
        JSON.DEFAULT_PARSER_FEATURE |=  Feature.UseBigDecimal.getMask();
        JSONObject jsonObject = JSON.parseObject("{\"id\":12,\"score\":4.5}");
        System.out.println(jsonObject);
        Person person = JSON.parseObject("{\n" +
                "\t\"id\": 2,\n" +
                "\t\"name\": \"sfl\",\n" +
                "\t\"address_full\": {\n" +
                "\t\t\"province\": \"js\",\n" +
                "\t\t\"place\": \"lyg\"\n" +
                "\t},\n" +
                "\t\"addresses\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"province\": \"js\",\n" +
                "\t\t\t\"place\": \"sz\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"province\": \"sh\",\n" +
                "\t\t\t\"place\": \"sh\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}", Person.class);
        System.out.println(person);
    }
}

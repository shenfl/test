package com.test.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Person p = work(Person.class);
        System.out.println(p.toString());
        // 当嵌套多层的时候
        HashMap<String, ArrayList<HashMap<String, Person>>> result = workNested(Person.class);
        System.out.println(result);
    }

    private static <T> T work(Class<T> tClass) {
        HashMap<String, T> parseObject = JSON.parseObject("{\n" +
                "\t\"_source\": {\n" +
                "\t\t\"id\": 33,\n" +
                "\t\t\"name\": \"shenfl\"\n" +
                "\t}\n" +
                "}", new TypeReference<HashMap<String, T>>(tClass) {
        });
        System.out.println(parseObject);
        return parseObject.get("_source");
    }
    private static <T> T workNested(Class tClass) {
        Type lastOne = new ParameterizedTypeImpl(new Type[]{String.class, tClass}, null, HashMap.class);
        Type lastTwo = new ParameterizedTypeImpl(new Type[]{lastOne}, null, ArrayList.class);
        Type lastThree = new ParameterizedTypeImpl(new Type[]{String.class, lastTwo}, null, HashMap.class);
        HashMap<String, T> hashMap = JSON.parseObject(
                "{\n" +
                    "\t\"hits\": {\n" +
                    "\t\t\"hits\": [\n" +
                    "\t\t\t{\n" +
                    "\t\t\t\t\"_source\": {\n" +
                    "\t\t\t\t\t\"id\": 33,\n" +
                    "\t\t\t\t\t\"name\": \"shenfl\"\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t]\n" +
                    "\t}\n" +
                    "}", new TypeReference<HashMap<String, T>>(lastThree) {
        });
        return hashMap.get("hits");
    }
}

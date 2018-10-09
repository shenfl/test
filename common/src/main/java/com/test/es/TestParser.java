package com.test.es;

import org.elasticsearch.cluster.ClusterModule;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shenfl on 2018/4/27
 */
public class TestParser {
    public static void main(String[] args) throws IOException {
        NamedXContentRegistry registry = new NamedXContentRegistry(ClusterModule.getNamedXWriteables());
        XContentParser parser = JsonXContent.jsonXContent.createParser(registry, "{\n"
                + "  \"test\" : \"foo\",\n"
                + "  \"test_number\" : 2,\n"
                + "  \"test_array\":  [1,2,3,4]\n"
                + "}");

        ObjectParser<TestStruct, Void> objectParser = new ObjectParser<>("foo");
        TestStruct s = new TestStruct();

        objectParser.declareField((i, c, x) -> c.test = i.text(), new ParseField("test"), ObjectParser.ValueType.STRING);
        objectParser.declareInt(TestStruct::setTestNumber, new ParseField("test_number"));
        objectParser.declareIntArray(TestStruct::setInts, new ParseField("test_array"));
        objectParser.parse(parser, s, null);
        System.out.println(s.test);
        System.out.println(s.testNumber);
        System.out.println(s.ints);
        System.out.println(objectParser.toString());
        System.out.println(s.test);




        XContentBuilder xContentBuilder = XContentFactory.contentBuilder(XContentType.JSON);
        xContentBuilder.startObject();
        xContentBuilder.field("test", "value");
        xContentBuilder.rawField("foo", new BytesArray("{\"test\":\"value\"}"));
        xContentBuilder.field("test1", "value1");
        xContentBuilder.endObject();
        System.out.println(xContentBuilder.bytes().utf8ToString());
        System.out.println(xContentBuilder.toString());

    }
    static class TestStruct {
        public String test;
        int testNumber;
        List<Integer> ints = new ArrayList<>();
        public void setTestNumber(int testNumber) {
            this.testNumber = testNumber;
        }

        public void setInts(List<Integer> ints) {
            this.ints = ints;
        }
    }
}

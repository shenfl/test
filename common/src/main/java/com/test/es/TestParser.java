package com.test.es;

import org.elasticsearch.cluster.ClusterModule;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.script.Script;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by shenfl on 2018/4/27
 */
public class TestParser {
    public static void main(String[] args) throws IOException {
        NamedXContentRegistry registry = new NamedXContentRegistry(ClusterModule.getNamedXWriteables());
        XContentParser parser = JsonXContent.jsonXContent.createParser(registry, DeprecationHandler.THROW_UNSUPPORTED_OPERATION, "{\n"
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

        // 构建json的string
        XContentBuilder xContentBuilder = XContentFactory.contentBuilder(XContentType.JSON);
        xContentBuilder.startObject();
        xContentBuilder.field("test", "value");
        xContentBuilder.field("test1", "value1");
        xContentBuilder.endObject();
        System.out.println(Strings.toString(xContentBuilder));

        // ConstructingObjectParser
        // 详见shenfl/ik GroupingMixupRescorerBuilder
        ConstructingObjectParser<TestScript, Void> PARSER =
                new ConstructingObjectParser<>(
                        "hello",
                        new Function<Object[], TestScript>() {
                            @Override
                            public TestScript apply(Object[] arg) {
                                System.out.println(arg[0]);
                                return new TestScript((String) arg[0], (Script) ((List)arg[1]).get(0));
                            }
                        }
                );
        PARSER.declareString(ConstructingObjectParser.constructorArg(), new ParseField("group_field"));
        PARSER.declareNamedObjects(ConstructingObjectParser.constructorArg(), (p, c, n) -> Script.parse(p), new ParseField("boost_script"));
        parser = JsonXContent.jsonXContent.createParser(registry, DeprecationHandler.THROW_UNSUPPORTED_OPERATION, "{\n" +
                "\t\"group_field\": \"foo\",\n" +
                "\t\"boost_script\": {\n" +
                "\t\t\"script\": {\n" +
                "\t\t\t\"lang\": \"grouping_mixup_scripts\",\n" +
                "\t\t\t\"source\": \"position_recip\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}");
        TestScript apply = PARSER.apply(parser, null);
        System.out.println(apply.test);
        System.out.println(apply.script.toString());


        //StreamInput StreamOutput主要用于网络通信
    }
    static class TestStruct {
        public String test;
        int testNumber;
        TestStruct() {}
        TestStruct(String test, int testNumber) {
            this.test = test;
            this.testNumber = testNumber;
        }
        List<Integer> ints = new ArrayList<>();
        public void setTestNumber(int testNumber) {
            this.testNumber = testNumber;
        }

        public void setInts(List<Integer> ints) {
            this.ints = ints;
        }
    }
    static class TestScript {
        public String test;
        public Script script;
        TestScript(String test, Script script) {
            this.test = test;
            this.script = script;
        }
    }
}

package com.test.antlr;

import org.antlr.v4.Tool;
import org.antlr.v4.gui.TestRig;
/**
 * @author shenfl
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // 生成代码
//        Tool.main(new String[] {"-package", "com.test.antlr.array", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/array/ArrayInit.g4"});
        // 执行
//        TestRig.main(new String[] {"com.test.antlr.idata.IData", "file", "-gui", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/idata/idata.txt"});

        // 生成代码
//        Tool.main(new String[] {"-package", "com.test.antlr.hello", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/hello/Hello.g4"});
        // 执行
//        TestRig.main(new String[] {"com.test.antlr.hello.Hello", "r", "-gui"});

        // 生成代码
//        Tool.main(new String[] {"-package", "com.test.antlr.rows", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/rows/Rows.g4"});

        // 生成代码
//        Tool.main(new String[] {"-package", "com.test.antlr.idata", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/idata/IData.g4"});
        //执行 antlr权威指南p101
        TestRig.main(new String[] {"com.test.antlr.idata.IData", "file", "-gui", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/idata/idata.txt"});
    }
}

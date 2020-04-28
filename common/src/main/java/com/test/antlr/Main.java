package com.test.antlr;

import org.antlr.v4.Tool;
import org.antlr.v4.gui.TestRig;
/**
 * @author shenfl
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // 生成代码
        Tool.main(new String[] {"-package", "com.test.antlr.array", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/array/ArrayInit.g4"});

        // 执行
        TestRig.main(new String[] {"com.test.antlr.idata.IData", "file", "-gui", "/Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/idata/idata.txt"});
    }
}

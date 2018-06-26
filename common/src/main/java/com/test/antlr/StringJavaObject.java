package com.test.antlr;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class StringJavaObject extends SimpleJavaFileObject {
    /**
     * 源代码
     */
    private String content = "";

    /**
     * 遵循Java规范的类名及文件
     */
    public StringJavaObject(String javaFileName, String content){
        super(_createStringJavaObjectUri(javaFileName), Kind.SOURCE);
        this.content = content;
    }

    /**
     * 产生一个URL资源路径
     */
    private static URI _createStringJavaObjectUri(String javaFileName) {
        //注意此处未设置包名
        return URI.create("String:///" + javaFileName + Kind.SOURCE.extension);
    }

    /**
     * 文本文件代码
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        return content;
    }
}
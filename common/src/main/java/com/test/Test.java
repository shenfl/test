package com.test;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shenfl on 2018/5/28
 */
public class Test {
    public static void main(String[] args) throws IOException, NoSuchMethodException {
//        IKSegmenter segmenter = new IKSegmenter(new StringReader(""), true);
////        segmenter.reset(new StringReader("人库及"));
//        segmenter.reset(new StringReader("kasdfe1213mnr"));
//        Lexeme lex = null;
//        while((lex = segmenter.next()) != null){
//            System.out.println(lex.getLexemeText());
//        }
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            System.currentTimeMillis();
//        }
//        System.out.println("cost: " + (System.currentTimeMillis() - start));
        Method register = ZooKeeper.class.getMethod("register", Watcher.class);
        System.out.println(register);
        Annotation[][] annotations = register.getParameterAnnotations();
        System.out.println(annotations.length);
        System.out.println(annotations[0].length);
        Map<String, String> map = new TreeMap<>();
        map.put("dfe", "www");
        map.put("hhfe", "g");
        map.put("aa", "hh");
        System.out.println(map);
    }
}

package com.test.annotation;

import org.reflections.Reflections;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Enter {
    public static void main(String[] args) {
        Reflections reflections = new Reflections("com.test.annotation1");
        Set<Class<? extends Node>> subTypesOf = reflections.getSubTypesOf(Node.class);
        for (Class<? extends Node> clazz: subTypesOf) {
            NodeType type = clazz.getAnnotation(NodeType.class);
            if (type != null) {
                System.out.println("llllllllll");
                System.out.println(type.value());
            }
        }
    }
}

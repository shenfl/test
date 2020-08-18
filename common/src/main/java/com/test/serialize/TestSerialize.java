package com.test.serialize;

import java.io.*;
import java.util.function.Function;

/**
 * 将lambda表达式实现通过序列化方式拿到方法名，参考baomidou的LambdaUpdateWrapper.set
 * search-client中的实现是将set方法转化出来的方法名转变为属性，放到map中，实现更新值为null
 * @author shenfl
 */
public class TestSerialize {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 报错，因为Function默认是不能序列化的
//        Function<String, Integer> function = String::length;
        SFunction<String, Integer> function = String::length;

        Class<?> clazz = function.getClass();
        System.out.println(clazz);

        ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(serialize(function))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz;
            }
        };
        Object o = objIn.readObject();
        System.out.println(o);
        System.out.println(((SFunction)o).apply("ae"));


        ///////////
        // 以上为正常过程

        objIn = new ObjectInputStream(new ByteArrayInputStream(serialize(function))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        };
        o = objIn.readObject();
        System.out.println(o);
        System.out.println(((SerializedLambda)o).getImplMethodName());

    }

    interface SFunction<T, R> extends Function<T, R>, Serializable {

    }

    private static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }
}

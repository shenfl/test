package com.test.java;

import java.lang.management.MonitorInfo;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shenfl on 2018/5/29
 */
public class ParameterizedTypeTest<T> {
    private List<T> myList = null;
    private Set<String> mySet = null;
    private Map.Entry<String, Integer> myEntry = null;
    private MonitorInfo monitorInfo;
    private List list;
    public static void main(String[] args) throws NoSuchFieldException {
        Field myList = ParameterizedTypeTest.class.getDeclaredField("myList");
        Type genericType = myList.getGenericType();
        System.out.println(genericType.getTypeName());
        System.out.println(genericType);
        Field mySet = ParameterizedTypeTest.class.getDeclaredField("mySet");
        Type genericType1 = mySet.getGenericType();
        System.out.println(genericType1);
        System.out.println(genericType1.getClass().getName());
        /**
         * 得到具体范型的类型
         */
        Type type = ((ParameterizedType) genericType1).getActualTypeArguments()[0];
        System.out.println(type);
        System.out.println(((ParameterizedType) genericType1).getRawType());
        System.out.println(((ParameterizedType) genericType1).getOwnerType());

        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        System.out.println(((TypeVariable)actualTypeArguments[0]).getBounds().length);


        //class
        Field monitorInfo = ParameterizedTypeTest.class.getDeclaredField("monitorInfo");
        System.out.println(monitorInfo.getGenericType());
        System.out.println(monitorInfo.getGenericType() instanceof Class);


        Class<ParameterizedTypeTest> parameterizedTypeTestClass = ParameterizedTypeTest.class;
        System.out.println(parameterizedTypeTestClass);


        Field list = ParameterizedTypeTest.class.getDeclaredField("list");
        Type genericType2 = list.getGenericType();
        System.out.println(genericType2);
        System.out.println(genericType2 instanceof Class);

    }
}

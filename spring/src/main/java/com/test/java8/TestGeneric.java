package com.test.java8;

/**
 * Created by shenfl on 2018/7/6
 * 通配符
 */
public class TestGeneric {
    public static void main(String[] args) {
        TestGeneric testGeneric = new TestGeneric();
        Generic<Integer> generic = new Generic<>(3);
        Integer integer = testGeneric.showKey(generic);
        System.out.println(integer);
        System.out.println("---------------");
        testGeneric.showKey1(generic);
        System.out.println("---------------");
//        testGeneric.showKey2(generic); // 编译报错
        testGeneric.showKey3(generic);
    }
    public <T> T showKey(Generic<T> param) {
        return param.getKey();
    }
    public void showKey1(Generic<?> param) {
        System.out.println(param.getKey());
    }
    public void showKey2(Generic<Number> param) {
        System.out.println(param.getKey());
    }
    public void showKey3(Generic<? extends Number> param) {
        Number key = param.getKey();
        System.out.println(key);
    }
    public void showKey4(Generic<? super Number> param) {
        Object key = param.getKey();
        System.out.println(key);
    }
    public static class Generic<T> {
        // 报错
//        public static void print(T t) {
//            System.out.println();
//        }
        private T key;
        public Generic(T key) {
            this.key = key;
        }
        public T getKey() {
            return key;
        }
    }
}

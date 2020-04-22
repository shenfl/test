package com.test.pattern.zerenlian;
/**
 * @author shenfl
 */
public interface Filter {
    int invoke(Invoker invoker, String data);
    static class FilterA implements Filter {

        @Override
        public int invoke(Invoker invoker, String data) {
            System.out.println("filter a.");
            int result = invoker.invoke(data);
            System.out.println("after filter a.");
            return result;
        }
    }
    static class FilterB implements Filter {

        @Override
        public int invoke(Invoker invoker, String data) {
            System.out.println("filter b");
            int result = invoker.invoke(data);
            System.out.println("after filter b.");
            return result;
        }
    }
}

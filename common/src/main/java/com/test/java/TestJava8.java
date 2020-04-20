package com.test.java;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * http://blog.csdn.net/54powerman/article/details/73188951
 */
public class TestJava8 {
    public static void main(String[] args) {
        TestJava8 main = new TestJava8();
        // 1
        // 其实函数式最不好理解的地方是这个地方的lambda表达式写的是函数式接口的方法体
        // 当然只有在调用方法体执行的时候才会使用方法名
        String time = main.getTime(() -> {
            System.out.println("qq");
            return "iii";
        });
        System.out.println(time);

        // 2 String范型限定了x的方法
        Predicate<String> pre=(x)-> x.startsWith("t_");
        boolean isTable=pre.test("dfdf");
        System.out.println(isTable);

        // 3 静态方法
        // 问题：根据Predicate定义，nonNull方法的参数这儿不得是boolean吗？
        // 是的，可以看到下面的test方法智能传入一个bool值，
        // 而用Objects::nonNull之所以可以是因为一个boolean值传给object是没问题的
        Predicate<Boolean> notNull = Objects::nonNull;
        System.out.println(notNull.test(false));


        // 构造器，函数式接口返回值一定是Person，入参是构造器参数
        Supplier<Person> sup = Person::new;
        System.out.println(sup);


        //对象方法，这儿使用String的isEmpty方法，所以参数必须是String
        // 且必须有string，且返回值必须是boolean，因为Predicate的test方法返回值是boolean
        Predicate<String> isEmpty = String::isEmpty; // 这个isEmpty函数的函数体就是接口的实现逻辑
        boolean dd = isEmpty.test("dd");
        System.out.println(dd);


        // 对象的属性方法，与静态方法一样
        Consumer<String> print = new Print()::println;
        print.accept("aa");


        String[] strs = {"zzaa","xxbb","yycc"};
        Arrays.sort(strs, String::compareToIgnoreCase);//OK
        System.out.println(Arrays.asList(strs));


        // PersonStudentFunction中的参数必须第一个是Person，第二个是com2的参数（Student）
        PersonStudentFunction f = Person::com2;
        f.test(new Person(), new Student());



        List<String> list = Arrays.asList(new String[] { "xxx", "aaa", "zzz", "eee", "yyy", "ccc" });
        Stream<String> stream1 = list.stream();
        Optional<String> reduce = stream1.reduce((x, y) -> x + y);
        System.out.println(reduce.get());


        // reduce
        System.out.println("reduce.......");
        BinaryOperator<String> accumulator= BinaryOperator.maxBy((x, y)->x.compareTo(y));
        Optional<String> max = list.stream()
                .reduce(accumulator);
        System.out.println(max);

        Arrays.asList("aa","bb","cc").forEach(System.out::println); // 这时候一定是 对象.方法

        StringBuilder joining = list.stream()
                .reduce(new StringBuilder(),
                        (x, e) -> {
                            System.out.println("2:t="+x+"; u="+e);
                            x.append(e).append(",");
                            return x;
                        },
                        (x, e) -> {
                            System.out.println("3:t="+x+"; u="+e);
                            x.append(e);
                            return x;
                        }
                );
        System.out.println(joining);

        boolean b = list.stream().allMatch(x->x.matches("a*"));
        System.out.println("AllMath:"+b);
        b = list.stream().anyMatch(x->x.matches("a*"));
        System.out.println("anyMatch:"+b);
        b = list.stream().noneMatch(x->x.matches("a*"));
        System.out.println("noneMatch:"+b);
    }

    static class Print {
        public void println(String s) {
            System.out.println(s);
        }
    }

    private String getTime(FunctionalInterfaceReturn fi) { // 只要参数是一个函数式接口，这个参数就可以传入lambda表达式
        return fi.generateTime() + "dddd";
    }
}

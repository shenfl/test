package com.test.spring.expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;

public class Test {
    public static void main(String[] args) throws NoSuchMethodException {

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        System.out.println(message);


        Expression exp1 = parser.parseExpression("new String('hello world').toUpperCase()");
        String message1 = exp1.getValue(String.class);
        System.out.println(message1);


        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
        Expression exp2 = parser.parseExpression("name"); // 必须要求name属性要是public或者有一个get方法
        EvaluationContext context = new StandardEvaluationContext(tesla);
        String name = (String) exp2.getValue(context);
        System.out.println(name);

        int year = (Integer) parser.parseExpression("Birthdate.Year + 1900").getValue(context);
        System.out.println(year);

        Society ieee = new Society();
        ieee.getMembers().add(tesla);
        StandardEvaluationContext societyContext = new StandardEvaluationContext(ieee);
        boolean isMember = parser.parseExpression("isMember('Mihajlo Pupin')").getValue(
                societyContext, Boolean.class);
        System.out.println("is number: " + isMember);

        parser.parseExpression("Members.add(new com.test.spring.expression.Inventor( 'Albert Einstein', 'German'))").getValue(societyContext);



        Simple simple = new Simple();
        simple.booleanList.add(true);
        StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);
        parser.parseExpression("booleanList[0]").setValue(simpleContext, "true");
        Boolean b = simple.booleanList.get(0);
        System.out.println(b);


        SpelParserConfiguration config = new SpelParserConfiguration(true,true);
        ExpressionParser parser1 = new SpelExpressionParser(config);
        Expression expression = parser1.parseExpression("list[3]");
        Demo demo = new Demo();
        Object o = expression.getValue(demo);
        System.out.println(o.equals(""));


        //计算公式
        Expression exp3 = parser.parseExpression("T(java.lang.Math).random() * 100.0");
        Double message3 = (Double) exp3.getValue();
        System.out.println(message3);


        StandardEvaluationContext context1 = new StandardEvaluationContext();
        context1.registerFunction("reverseString",
                StringUtils.class.getDeclaredMethod("reverseString", String.class));
        String helloWorldReversed = parser.parseExpression("#reverseString('hello')").getValue(context1, String.class);
        System.out.println(helloWorldReversed);




    }

    static class Demo {
        public List<String> list;
    }

    static class Simple {
        public List<Boolean> booleanList = new ArrayList<Boolean>();
    }

    public enum AppType {
        NORMAL, BYHAND
    }
    static class A {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}

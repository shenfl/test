package com.test.spring;

import org.springframework.beans.factory.InitializingBean;

public class Welcomer implements InitializingBean {

    private MessageLocator message;
    private String a = get();
    private static String b = ggg();
    static {
        System.out.println("df");
    }

    public Welcomer(){
        System.out.println("bb");
    }
    private static String get() {
        System.out.println("kk");
        return "sds";
    }
    private static String ggg() {
        System.out.println("ds");
        return "s";
    }

    public Welcomer(MessageLocator message) {
        System.out.println("hhh");
        this.message = message;
    }

    public MessageLocator getMessage() {
        return message;
    }

    public void setMessage(MessageLocator message) {
        System.out.println("rrr");
        this.message = message;
    }

    public static Welcomer createWelcomer(MessageLocator messageLocator) {
        System.out.println("create welcomer");
        return new Welcomer(messageLocator);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    public static class MessageLocator{
        String source;
        public MessageLocator() {
            System.out.println("aa");
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}

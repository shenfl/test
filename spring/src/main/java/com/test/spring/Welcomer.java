package com.test.spring;

public class Welcomer {

    private MessageLocator message;

    public Welcomer(){}

    public Welcomer(MessageLocator message) {
        this.message = message;
    }

    public MessageLocator getMessage() {
        return message;
    }

    public static Welcomer createWelcomer(MessageLocator messageLocator) {
        System.out.println("create welcomer");
        return new Welcomer(messageLocator);
    }

    public static class MessageLocator{
        String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}

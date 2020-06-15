package com.test.spring;

public class Welcomer {

    private MessageLocator message;

    public Welcomer(){
        System.out.println("bb");
    }

    public Welcomer(MessageLocator message) {
        this.message = message;
    }

    public MessageLocator getMessage() {
        return message;
    }

    public void setMessage(MessageLocator message) {
        this.message = message;
    }

    public static Welcomer createWelcomer(MessageLocator messageLocator) {
        System.out.println("create welcomer");
        return new Welcomer(messageLocator);
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

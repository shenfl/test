package com.test.rmi;

/**
 * @author shenfl
 */
import java.rmi.Naming;

public class RmiClient {

    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost:1099/com.test.rmi.HelloServiceImpl" ;
        HelloService helloService = (HelloService) Naming.lookup( url);
        String result = helloService .sayHello("Jack");
        System. out.println(result );
    }
}
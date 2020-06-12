package com.test.rmi;

/**
 * http://www.tianshouzhi.com/api/tutorials/zookeeper/219
 * @author shenfl
 */
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer {

    public static void main(String[] args) throws Exception {
        int port = 1099;
        String url = "rmi://localhost:1099/com.test.rmi.HelloServiceImpl" ;
        LocateRegistry. createRegistry(port);
        Naming. rebind(url, new HelloServiceImpl());
    }
}
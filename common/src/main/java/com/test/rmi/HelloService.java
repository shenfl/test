package com.test.rmi;

/**
 * @author shenfl
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {

    String sayHello(String name) throws RemoteException;
}

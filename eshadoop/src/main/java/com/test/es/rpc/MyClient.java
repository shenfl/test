package com.test.es.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class MyClient {

    private MyProtocol proxy;

    public MyClient() {
        InetSocketAddress addr = new InetSocketAddress("localhost", 9999);
        try {
            //构造客户端代理对象，直接通过代理对象调用远程端的方法
            proxy = (MyProtocol) RPC.waitForProxy(MyProtocol.class, MyProtocol.versionID, addr, new Configuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyProtocol getProxy() {
        return proxy;
    }

    public void close(){
        RPC.stopProxy(proxy);
    }

    public static void main(String[] args) {
        MyClient c = new MyClient();
        Scanner cin = new Scanner(System.in);

        //字符串
        System.out.println("字符串：");
        System.out.print("send: ");
        String str = cin.next();
        String strAns = c.getProxy().println(str);
        System.out.println("receive: " + strAns);
        System.out.println();

        //相乘
        System.out.println("相乘：");
        System.out.print("send: ");
        int a = cin.nextInt();
        int b = cin.nextInt();
        int cAns = c.getProxy().mul(a, b);
        System.out.println("receive: " + cAns);
        System.out.println();

        //排序
        System.out.println("排序：");
        int[] nums = new int[1000];
        System.out.print("send(end with #): ");
        int len = 0;
        while(cin.hasNext()) {
            String tmp = cin.next();
            if(tmp.equals("#")) break;
            nums[len++] = Integer.parseInt(tmp);
        }
        int[] numsAns = c.getProxy().sortGreat(nums, len);
        System.out.print("receive: ");
        for(int i=0; i<len; i++) {
            System.out.print(numsAns[i] + " ");
        }
        System.out.println();

        c.close();
        cin.close();
    }
}
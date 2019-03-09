package com.test.es.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://blog.csdn.net/thinking2013/article/details/45619075
 */
public class MyServer implements MyProtocol {
    private RPC.Server server;

    public MyServer() {
        try {
            Configuration conf = new Configuration();

            /* BindAddress和Port分别表示服务器的host和监听端口号，
             *  而NnumHandlers表示服务器端处理请求的线程数目;
             */
            server = new RPC.Builder(conf)
                    .setProtocol(MyServer.class).setInstance(this)
                    .setBindAddress("localhost").setPort(9999).setNumHandlers(5)
                    .build();
            server.start();

            System.out.println("Server is running: "
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            server.join();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //重载的方法，用于获取自定义的协议版本号
    @Override
    public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
        return MyProtocol.versionID;
    }

    //重载的方法，用于获取协议签名
    @Override
    public ProtocolSignature getProtocolSignature(String arg0, long arg1,
                                                  int arg2) throws IOException {
        // TODO Auto-generated method stub
        return new ProtocolSignature(MyProtocol.versionID, null);
    }

    @Override
    public String println(String t) {
        System.out.println("receive: " + t);
        return "Hello " + t;
    }

    @Override
    public int mul(int a, int b) {
        System.out.println("receive: " + a + " " + b);
        return a*b;
    }

    @Override
    public int[] sortGreat(int[] nums, int len) {
        System.out.print("receive: ");
        for(int i=0; i<len; i++) System.out.print(nums[i] + " ");
        System.out.println();

        for(int i=0; i<len; i++) {
            for(int j=i+1; j<len; j++) {
                if(nums[i] > nums[j]) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        new MyServer();
    }
}
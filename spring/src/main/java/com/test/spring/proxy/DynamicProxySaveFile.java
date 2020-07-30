package com.test.spring.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 保存生成文件
 * https://www.cnblogs.com/jhxxb/p/10557738.html
 * @author shenfl
 */
public class DynamicProxySaveFile {
    /**
     * 保存 JDK 动态代理生产的类
     * @param filePath 保存路径，默认在项目路径下生成 $Proxy0.class 文件
     */
    private static void saveProxyFile(String... filePath) {
        if (filePath.length == 0) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        } else {
            FileOutputStream out = null;
            try {
                byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", UserDaoImpl.class.getInterfaces());
                out = new FileOutputStream(filePath[0] + "$Proxy0.class");
                out.write(classFile);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        saveProxyFile("/Users/shenfl/work");

        Object target = new UserDaoImpl();

        /**
         * loader：业务对象的类加载器
         * interfaces：业务对象实现的所有接口
         * public static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces)
         */
        Class<?> proxyClass = Proxy.getProxyClass(UserDaoImpl.class.getClassLoader(), UserDaoImpl.class.getInterfaces());
        InvocationHandler handler = new InvocationHandler() {
            /**
             * @param proxy 代理对象
             * @param method 代理的方法对象
             * @param args 方法调用时参数
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                if (method.getName().equals("save")) {
                    System.out.println("before...");
                    result = method.invoke(target, args);
                    System.out.println("after...");
                }
                return result;
            }
        };
        UserDao userDao = (UserDao) proxyClass.getConstructor(InvocationHandler.class).newInstance(handler);
        userDao.save();
    }
}

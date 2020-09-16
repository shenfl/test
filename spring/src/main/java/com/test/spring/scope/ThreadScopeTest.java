package com.test.spring.scope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * https://zhuanlan.zhihu.com/p/110002719
 * @author shenfl
 */
public class ThreadScopeTest {
    public static void main(String[] args) throws InterruptedException {
        String beanXml = "applicationContext-scope.xml";
        //手动创建容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        //设置配置文件位置
        context.setConfigLocation(beanXml);
        //启动容器
        context.refresh();
        //向容器中注册自定义的scope
        context.getBeanFactory().registerScope(ThreadScope.THREAD_SCOPE, new ThreadScope());//@1

        //使用容器获取bean
        for (int i = 0; i < 2; i++) { //@2
            new Thread(() -> {
                System.out.println(Thread.currentThread() + "," + context.getBean("threadBean"));
                System.out.println(Thread.currentThread() + "," + context.getBean("threadBean"));
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
package com.test.spring.factorybean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shenfl on 2018/8/31
 * https://www.cnblogs.com/redcool/p/6413461.html
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-factorybean.xml")
public class MyFactoryBeanTest {

    @Autowired
    private ApplicationContext context;

    /**
     * 测试验证FactoryBean原理，代理一个servcie在调用其方法的前后，打印日志亦可作其他处理
     * 从ApplicationContext中获取自定义的FactoryBean
     * context.getBean(String beanName) ---> 最终获取到的Object是FactoryBean.getObejct(),
     * 使用Proxy.newInstance生成service的代理类
     */
    @Test
    public void testFactoryBean() {
        HelloWorldService helloWorldService = (HelloWorldService) context.getBean("factoryBean");
        helloWorldService.getBeanName();
        helloWorldService.sayHello();
        Object bean = context.getBean("&factoryBean"); // 拿到原始的factoryBean
        System.out.println(bean);
    }
}
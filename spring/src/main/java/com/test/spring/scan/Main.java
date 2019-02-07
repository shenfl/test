package com.test.spring.scan;

import org.springframework.context.support.GenericApplicationContext;

/**
 * https://www.jianshu.com/p/d5ffdccc4f5d
 * 测试spring自动扫描
 */
public class Main {
    public static void main(String[] args) {
        String BASE_PACKAGE = "com.test.spring.scan";
        GenericApplicationContext context = new GenericApplicationContext();
        MyClassPathDefinitionScanner myClassPathDefinitonScanner = new MyClassPathDefinitionScanner(context, MyBean.class);
        // 注册过滤器
        myClassPathDefinitonScanner.registerTypeFilter();
        int beanCount = myClassPathDefinitonScanner.scan(BASE_PACKAGE);
        context.refresh();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(beanCount);
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}

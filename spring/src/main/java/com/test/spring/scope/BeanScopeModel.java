package com.test.spring.scope;

/**
 * @author shenfl
 */
public class BeanScopeModel {
    public BeanScopeModel(String beanScope) {
        System.out.println(String.format("线程:%s,create BeanScopeModel,{sope=%s},{this=%s}", Thread.currentThread(), beanScope, this));
    }
}
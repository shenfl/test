package com.test.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by shenfl on 2018/8/24
 */
public class PeopleNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("people", new PeopleBeanDefinationParser());
    }
}

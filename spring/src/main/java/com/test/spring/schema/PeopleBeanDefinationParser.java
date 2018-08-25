package com.test.spring.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Created by shenfl on 2018/8/25
 */
public class PeopleBeanDefinationParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return People.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");
        String id = element.getAttribute("id");
        builder.addPropertyValue("id", id);
        builder.addPropertyValue("name", name);
        builder.addPropertyValue("age", Integer.valueOf(age));
    }
}

package com.test.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by shenfl on 2018/8/24
 */
public class PeopleNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("people", new PeopleBeanDefinationParser());
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return super.parse(element, parserContext);
    }
}

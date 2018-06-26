package com.test.spring.defineTag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class TagsNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        //自定义标签中的element标签名为client解析注册使用MysqlMapClientPraser进行.
        registerBeanDefinitionParser("client", new MysqlMapClientPraser());
    }
}

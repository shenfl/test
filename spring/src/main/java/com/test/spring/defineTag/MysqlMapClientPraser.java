package com.test.spring.defineTag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.w3c.dom.Element;

public class MysqlMapClientPraser extends AbstractSimpleBeanDefinitionParser {

    /**
     * element 相当于对应的element元素 parserContext 解析的上下文 builder 用于该标签的实现
     */
    @Override
    protected void doParse(Element element, ParserContext parserContext,
                           BeanDefinitionBuilder builder) {

        // 从标签中取出对应的属性值
        String dbname = element.getAttribute("dbname");
        String datasouceip = element.getAttribute("datasouceip");
        String username = element.getAttribute("username");
        String password = element.getAttribute("password");
        String characterEncoding = element.getAttribute("characterEncoding");
        String configLocation = element.getAttribute("configLocation");
        final String driverClassName = "com.mysql.jdbc.Driver";

        // System.out.println("dbname" + dbname);
        // System.out.println("datasouceip" + datasouceip);
        // System.out.println("username" + username);
        // System.out.println("password" + password);
        // System.out.println("characterEncoding" + characterEncoding);
        // System.out.println("configLocation" + configLocation);

        final StringBuffer url = new StringBuffer("jdbc:mysql://");
        url.append(datasouceip).append("/").append(dbname).append(
                "?useUnicode=true").append("&").append(
                "characterEncoding=" + characterEncoding).append(
                "&autoReconnect=true");

        // 创建 datasource实例
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName(driverClassName);
        // System.out.println(url.toString());
        datasource.setUrl(url.toString());
        datasource.setUsername(username);
        datasource.setPassword(password);


        // 把创建完的实例对应的传到该标签类实现的相应属性中
        builder.addPropertyValue("dataSource", datasource);
    }

    @Override
    protected Class getBeanClass(Element element) {
        // 返回该标签所定义的类实现,在这里是为了创建出SqlMapClientTemplate对象
        return DataSourceWraper.class;
    }

}

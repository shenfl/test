package com.test.spring.propertyRegistry;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;
import java.util.logging.Logger;

public class RemoteProperties implements InitializingBean, FactoryBean<Properties> {

    private String url = null;

    private Properties properties = new Properties();


    @Override
    public Properties getObject() throws Exception {
        return properties;
    }

    @Override
    public Class<?> getObjectType() {
        return properties.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadProperty();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void loadProperty() {
        properties.put("aa", "AA");
        properties.put("url", url);
    }
}
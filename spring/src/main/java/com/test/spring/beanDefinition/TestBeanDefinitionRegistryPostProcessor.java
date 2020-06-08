package com.test.spring.beanDefinition;

import com.test.spring.importAnnotation.Dog;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author shenfl
 */
public class TestBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("TestBeanDefinitionRegistryPostProcessor...postProcessBeanDefinitionRegistry");
        System.out.println(registry.getBeanDefinitionCount());

        //RootBeanDefinition beanDefinition = new RootBeanDefinition(Dog.class);  作用同下行
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Dog.class).getBeanDefinition();
        registry.registerBeanDefinition("dog",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("TestBeanDefinitionRegistryPostProcessor...postProcessBeanFactory");
        BeanDefinition dog = beanFactory.getBeanDefinition("dog");
        MutablePropertyValues pvs = dog.getPropertyValues();
        pvs.addPropertyValue("id",12);
        System.out.println(beanFactory.getBeanDefinitionCount());
    }
}

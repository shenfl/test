package com.test.spring.beanDefinition;

import com.test.spring.importAnnotation.Dog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * https://blog.csdn.net/newbie0107/article/details/100129360
 * @author shenfl
 */
@Configuration
@Import(TestBeanDefinitionRegistryPostProcessor.class)
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        Dog dog = applicationContext.getBean("dog", Dog.class);
        System.out.println(dog.getId());
    }
}

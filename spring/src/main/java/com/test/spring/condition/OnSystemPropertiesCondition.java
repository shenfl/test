package com.test.spring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

/**
 * @author shenfl
 */
public class OnSystemPropertiesCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(ConditionOnSystemProperties.class.getName());
        String name = String.valueOf(attributes.get("name").get(0));
        String value = String.valueOf(attributes.get("value").get(0));
        String property = System.getProperties().getProperty(name);
        return value.equals(property);
    }
}

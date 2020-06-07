package com.test.spring.condition;/**
 * @author shenfl
 */

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author shenfl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnSystemPropertiesCondition.class)
public @interface ConditionOnSystemProperties {
    String name();
    String value();
}

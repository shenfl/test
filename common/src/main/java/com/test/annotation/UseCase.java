package com.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2015/12/14.
 */
@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.CLASS)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    public int id() default -1;
    public String description() default "No description";
}

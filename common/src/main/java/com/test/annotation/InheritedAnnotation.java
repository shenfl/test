package com.test.annotation;

import java.lang.annotation.*;

/**
 * Created by shenfl on 2018/8/6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InheritedAnnotation {
}

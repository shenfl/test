package com.test.spring.importSelect;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by shenfl on 2020-01-09
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(SpringStudySelector.class)
public @interface EnableSpringStudy {
}

package com.test.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * Created by shenfl on 2018/6/17
 */
@Aspect
public class MyAspectAnno implements Ordered {
    @Before(value = "MyAspectAnno.fn()")
    public void log() {
        System.out.println("annotation log");
    }
    @After(value = "MyAspectAnno.fn()")
    public void after() {
        System.out.println("annotation after");
    }
    // 切面只能织入spring管理的对象，所以||后面的无效
    @Pointcut(value = "execution(public void com.test.spring.proxy.UserDaoImpl.save()) || execution(public void com.test.spring.aop.Main.staticPoint())")
    private void fn() {} // public private无所谓

    @Override
    public int getOrder() { // 值越小优先级高
        return 0;
    }

    @Around(value = "MyAspectAnno.fn1()")
    public Object annotationAround(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);

        if (annotation == null) {
            System.out.println("代理的方法没有这个注解，需要找到源方法");
            try {
                method = proceedingJoinPoint.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
                annotation = method.getAnnotation(MyAnnotation.class);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

        System.out.println(annotation.value());
        try {
            System.out.println("annotation before aspect");
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Pointcut(value = "@annotation(com.test.spring.aop.MyAnnotation)")
    private void fn1() {}
}

package com.test.spring.proxy;

import org.springframework.cglib.proxy.*;
import java.lang.reflect.Method;

/**
 * https://www.cnblogs.com/shijiaqi1066/p/3429691.html
 */
public class TestCallBackFilter {
    public static void main(String[] args) {
        // 回调实例数组
        Callback[] callbacks = new Callback[] { new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("filter cglib proxy before");
                Object res = methodProxy.invokeSuper(o, objects);
                System.out.println("filter cglib proxy after");
                return res;
            }
        }, NoOp.INSTANCE };

        // 使用enhancer，设置相关参数。
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDaoImpl.class);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new CallbackFilterImpl());

        // 产生代理对象
        UserDaoImpl proxyUser = (UserDaoImpl) enhancer.create();
        proxyUser.load();
        proxyUser.save();


    }
}

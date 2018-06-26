package com.test.spring.listenerAsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/products/change-failure")
    public String changeFailure() {
        try {
            System.out.println("I'm modifying the product but a NullPointerException will be thrown");
            String name = null;
            if (name.isEmpty()) {
                // show error message here
                throw new RuntimeException("NullPointerException");
            }
        } catch (Exception e) {
            context.publishEvent(new ProductChangeFailureEvent(context));
        }
        return "success";
    }


    @RequestMapping(value = "/products/change-success")
    public String changeSuccess() {
        System.out.println("Product was correctly changed");
        context.publishEvent(new NotifMailDispatchEvent(context));
        return "success";
    }
}
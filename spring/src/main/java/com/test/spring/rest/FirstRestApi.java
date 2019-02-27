package com.test.spring.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenfl on 2018/6/25
 */
@RestController
@RequestMapping("/test")
public class FirstRestApi {
    @RequestMapping("/fitst")
    public boolean first() {
        return false;
    }
}

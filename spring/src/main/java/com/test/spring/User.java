package com.test.spring;

import org.springframework.beans.factory.InitializingBean;

public class User implements InitializingBean {
    private String username;
    private String password;
    public User() {
        System.out.println("User create..");
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return "李四";
    }
    public String getPassword() {
        return "";
    }
    public void printUser(){
        System.out.println("当前用户的用户名："+username+" ,密码："+password);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("user: " + username);
    }
}

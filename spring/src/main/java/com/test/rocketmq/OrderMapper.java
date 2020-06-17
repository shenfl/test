package com.test.rocketmq;/**
 * @author shenfl
 */

import org.springframework.stereotype.Component;

/**
 * @author shenfl
 */
@Component
public class OrderMapper {
    public void updateOrder(Order order) {
        System.out.println("模拟数据库保存成功: " + order.toString());
    }
}

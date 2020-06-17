package com.test.rocketmq;/**
 * @author shenfl
 */

import org.springframework.stereotype.Component;

/**
 * @author shenfl
 */
@Component
public class PointRecordMapper {
    public void insert(PointRecord record) {
        System.out.println("insert " + record.toString());
    }

    public PointRecord getPointRecordByOrderNo(String orderNo) {
        System.out.println("check: " + orderNo);
        // 模拟查到
        return new PointRecord();
    }
}

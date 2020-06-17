package com.test.rocketmq;

/**
 * @author shenfl
 */
public class PointRecord {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Integer userId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PointRecord{" +
                "orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                '}';
    }
}
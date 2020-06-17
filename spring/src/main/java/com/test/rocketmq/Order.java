package com.test.rocketmq;

import java.util.Date;

/**
 * @author shenfl
 */
public class Order {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 买家id
     */
    private Integer buyerId;

    /**
     * 支付状态 0 已支付 1 未支付 2 已超时
     */
    private Integer payStatus;

    /**
     * 下单日期
     */
    private Date createDate;

    /**
     * 金额
     */
    private Long amount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", buyerId=" + buyerId +
                ", payStatus=" + payStatus +
                ", createDate=" + createDate +
                ", amount=" + amount +
                '}';
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

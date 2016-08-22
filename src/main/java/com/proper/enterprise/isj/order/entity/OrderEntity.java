package com.proper.enterprise.isj.order.entity;


import com.proper.enterprise.platform.core.annotation.CacheEntity;
import com.proper.enterprise.platform.core.entity.BaseEntity;
import com.proper.enterprise.isj.order.model.Order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ISJ_WEIXIN_ORDERINFO")
//@DiscriminatorColumn(name = "pepDtype")
//@DiscriminatorValue("WeixinEntity")
@CacheEntity
public class OrderEntity extends BaseEntity implements Order {

    public OrderEntity(){ }

    public OrderEntity(String orderNo, int paymentStatus, int isdel) {
        this.orderNo = orderNo;
        this.paymentStatus = paymentStatus;
        this.isdel = isdel;
    }

    /**
     * 订单号
     */
    @Column(unique = true, nullable = false)
    private String orderNo;

    /**
     * 支付状态
     *
     * 0 : 未支付
     * 1 : 支付待确认
     * 2 : 支付成功
     * 4 : 支付失败
     */
    @Column(nullable = false)
    private int paymentStatus;

    /**
     * 逻辑删除
     *
     * 0 : 正常
     * 1 : 逻辑删除
     */
    @Column(nullable = false)
    private int isdel;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

}

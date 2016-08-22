package com.proper.enterprise.isj.order.model;


import com.proper.enterprise.platform.core.api.IBase;

public interface Order extends IBase{

    String getOrderNo();

    void setOrderNo(String orderNo);

    int getPaymentStatus();

    void setPaymentStatus(int paymentStatus);

    int getIsdel();

    void setIsdel(int isdel);
}

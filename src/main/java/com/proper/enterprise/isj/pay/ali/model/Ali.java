package com.proper.enterprise.isj.pay.ali.model;

import com.proper.enterprise.platform.core.api.IBase;

import java.util.Date;

/**
 * 支付宝
 */
public interface Ali extends IBase{

    Date getNotifyTime();

    void setNotifyTime(Date notifyTime);

    String getNotifyType();

    void setNotifyType(String notifyType);

    String getNotifyId();

    void setNotifyId(String notifyId);

    String getSignType();

    void setSignType(String signType);

    String getSign();

    void setSign(String sign);

    String getOutTradeNo();

    void setOutTradeNo(String outTradeNo);

    String getSubject();

    void setSubject(String subject);

    String getPaymentType();

    void setPaymentType(String paymentType);

    String getTradeNo();

    void setTradeNo(String tradeNo);

    String getTradeStatus();

    void setTradeStatus(String tradeStatus);

    String getSellerId();

    void setSellerId(String sellerId);

    String getSellerEmail();

    void setSellerEmail(String sellerEmail);

    String getBuyerId();

    void setBuyerId(String buyerId);

    String getBuyerEmail();

    void setBuyerEmail(String buyerEmail);

    String getTotalFee();

    void setTotalFee(String totalFee);

    String getQuantity();

    void setQuantity(String quantity);

    String getPrice();

    void setPrice(String price);

    String getBody();

    void setBody(String body);

    Date getGmtCreate();

    void setGmtCreate(Date gmtCreate);

    Date getGmtPayment();

    void setGmtPayment(Date gmtPayment);

    String getIsTotalFeeAdjust();

    void setIsTotalFeeAdjust(String isTotalFeeAdjust);

    String getUseCoupon();

    void setUseCoupon(String useCoupon);

    String getDiscount();

    void setDiscount(String discount);

    String getRefundStatus();

    void setRefundStatus(String refundStatus);

    Date getGmtRefund();

    void setGmtRefund(Date gmtRefund);

    int getIsdel();

    void setIsdel(int isdel);
}

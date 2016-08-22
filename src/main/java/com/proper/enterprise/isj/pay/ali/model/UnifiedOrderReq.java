package com.proper.enterprise.isj.pay.ali.model;

/**
 * 支付宝接收前台请求支付信息参数
 */
public class UnifiedOrderReq {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String subject;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品总金额
     */
    private String totalFee;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
}

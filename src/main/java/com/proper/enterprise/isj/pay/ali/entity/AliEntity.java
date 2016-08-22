package com.proper.enterprise.isj.pay.ali.entity;

import com.proper.enterprise.isj.pay.ali.model.Ali;
import com.proper.enterprise.platform.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 支付宝Entity
 */
@Entity
@Table(name = "ISJ_ALI_PAYINFO")
public class AliEntity extends BaseEntity implements Ali {

    public AliEntity() { }

    public AliEntity(Date notifyTime, String notifyType, String notifyId, String signType, String sign, String outTradeNo, String subject, String paymentType, String tradeNo, String tradeStatus, String sellerId, String sellerEmail, String buyerId, String buyerEmail, String totalFee, String quantity, String price, String body, Date gmtCreate, Date gmtPayment, String isTotalFeeAdjust, String useCoupon, String discount, String refundStatus, Date gmtRefund, int isdel) {
        this.notifyTime = (Date)notifyTime.clone();
        this.notifyType = notifyType;
        this.notifyId = notifyId;
        this.signType = signType;
        this.sign = sign;
        this.outTradeNo = outTradeNo;
        this.subject = subject;
        this.paymentType = paymentType;
        this.tradeNo = tradeNo;
        this.tradeStatus = tradeStatus;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
        this.totalFee = totalFee;
        this.quantity = quantity;
        this.price = price;
        this.body = body;
        this.gmtCreate = (Date)gmtCreate.clone();
        this.gmtPayment = (Date)gmtPayment.clone();
        this.isTotalFeeAdjust = isTotalFeeAdjust;
        this.useCoupon = useCoupon;
        this.discount = discount;
        this.refundStatus = refundStatus;
        this.gmtRefund = (Date)gmtRefund.clone();
        this.isdel = isdel;
    }

    /**
     * 通知时间 不可空
     */
    @Column(nullable = false)
    private Date notifyTime;

    /**
     * 通知类型 不可空
     */
    @Column(nullable = false)
    private String notifyType;

    /**
     * 通知校验ID 不可空
     */
    @Column(nullable = false)
    private String notifyId;

    /**
     * 签名方式 不可空
     */
    @Column(nullable = false)
    private String signType;

    /**
     * 签名 不可空
     */
    @Column(nullable = false)
    private String sign;

    /**
     * 商户网站唯一订单号 可空
     */
    private String outTradeNo;

    /**
     * 商品名称 可空
     */
    private String subject;

    /**
     * 支付类型 可空
     */
    private String paymentType;

    /**
     * 支付宝交易号 不可空
     */
    @Column(nullable = false)
    private String tradeNo;

    /**
     * 交易状态 不可空
     */
    @Column(nullable = false)
    private String tradeStatus;

    /**
     * 卖家支付宝用户号 不可空
     */
    @Column(nullable = false)
    private String sellerId;

    /**
     * 卖家支付宝账号 不可空
     */
    @Column(nullable = false)
    private String sellerEmail;

    /**
     * 买家支付宝用户号 不可空
     */
    @Column(nullable = false)
    private String buyerId;

    /**
     * 买家支付宝账号 不可空
     */
    @Column(nullable = false)
    private String buyerEmail;

    /**
     * 交易金额 不可空
     */
    @Column(nullable = false)
    private String totalFee;

    /**
     * 购买数量 可空
     */
    private String quantity;

    /**
     * 商品单价 可空
     */
    private String price;

    /**
     * 商品描述 可空
     */
    private String body;

    /**
     * 交易创建时间 可空
     */
    private Date gmtCreate;

    /**
     * 交易付款时间 可空
     */
    private Date gmtPayment;

    /**
     * 是否调整总价 可空
     */
    private String isTotalFeeAdjust;

    /**
     * 是否使用红包买家 可空
     */
    private String useCoupon;

    /**
     * 折扣 可空
     */
    private String discount;

    /**
     * 退款状态 可空
     */
    private String refundStatus;

    /**
     * 退款时间 可空
     */
    private Date gmtRefund;

    /**
     * 逻辑删除 0:正常/1:逻辑删除
     */
    @Column(nullable = false)
    private int isdel;

    public Date getNotifyTime() {
        return (Date)notifyTime.clone();
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = (Date)notifyTime.clone();
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getGmtCreate() {
        return (Date)gmtCreate.clone();
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = (Date)gmtCreate.clone();
    }

    public Date getGmtPayment() {
        return (Date)gmtPayment.clone();
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = (Date)gmtPayment.clone();
    }

    public String getIsTotalFeeAdjust() {
        return isTotalFeeAdjust;
    }

    public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
        this.isTotalFeeAdjust = isTotalFeeAdjust;
    }

    public String getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(String useCoupon) {
        this.useCoupon = useCoupon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getGmtRefund() {
        return (Date)gmtRefund.clone();
    }

    public void setGmtRefund(Date gmtRefund) {
        this.gmtRefund = (Date)gmtRefund.clone();
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }
}
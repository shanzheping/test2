package com.proper.enterprise.isj.pay.weixin.model;

import com.proper.enterprise.platform.core.api.IBase;

import java.util.Date;

/**
 * 微信异步通知
 *
 * 用于记录微信异步通知的结果
 *
 */
public interface Weixin extends IBase {

    String getReturnCode();

    void setReturnCode(String returnCode);

    String getReturnMsg();

    void setReturnMsg(String returnMsg);

    String getAppid();

    void setAppid(String appid);

    String getMchId();

    void setMchId(String mchId);

    String getDeviceInfo();

    void setDeviceInfo(String deviceInfo);

    String getNonceStr();

    void setNonceStr(String nonceStr);

    String getSign();

    void setSign(String sign);

    String getResultCode();

    void setResultCode(String resultCode);

    String getErrCode();

    void setErrCode(String errCode);

    String getErrCodeDes();

    void setErrCodeDes(String errCodeDes);

    String getOpenid();

    void setOpenid(String openid);

    String getIsSubscribe();

    void setIsSubscribe(String isSubscribe);

    String getTradeType();

    void setTradeType(String tradeType);

    String getBankType();

    void setBankType(String bankType);

    int getTotalFee();

    void setTotalFee(int totalFee);

    String getFeeType();

    void setFeeType(String feeType);

    int getCashFee();

    void setCashFee(int cashFee);

    String getCashFeeType();

    void setCashFeeType(String cashFeeType);

    int getCouponFee();

    void setCouponFee(int couponFee);

    int getCouponCount();

    void setCouponCount(int couponCount);

    String getTransactionId();

    void setTransactionId(String transactionId);

    String getOutTradeNo();

    void setOutTradeNo(String setOutTradeNo);

    String getAttach();

    void setAttach(String attach);

    String getTimeEnd();

    void setTimeEnd(String timeEnd);

    int getIsdel();

    void setIsdel(int isdel);
}

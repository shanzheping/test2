package com.proper.enterprise.isj.pay.weixin.service;

import com.proper.enterprise.isj.pay.weixin.model.Weixin;

/**
 * 微信支付服务接口
 *
 */
public interface WeixinService {

    Weixin save(Weixin weixin);

    Weixin findByOutTradeNo(String outTradeNo);

}

package com.proper.enterprise.isj.pay.ali.service;

import com.proper.enterprise.isj.pay.ali.model.Ali;

/**
 * 微信支付服务接口
 *
 */
public interface AliService {

    Ali save(Ali ali);

    Ali findByOutTradeNo(String outTradeNo);

}

package com.proper.enterprise.isj.pay.weixin.service.impl;

import com.proper.enterprise.isj.pay.weixin.entity.WeixinEntity;
import com.proper.enterprise.isj.pay.weixin.model.Weixin;
import com.proper.enterprise.isj.pay.weixin.repository.WeixinRepository;
import com.proper.enterprise.isj.pay.weixin.service.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeixinServiceImpl implements WeixinService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinServiceImpl.class);

    @Autowired
    WeixinRepository weixinRepo;

    @Override
    public Weixin save(Weixin weixin){
        return weixinRepo.save((WeixinEntity) weixin);
    }

    @Override
    public Weixin findByOutTradeNo(String outTradeNo) {
        return weixinRepo.findByOutTradeNo(outTradeNo);
    }
}

package com.proper.enterprise.isj.pay.ali.service.impl;


import com.proper.enterprise.isj.pay.ali.entity.AliEntity;
import com.proper.enterprise.isj.pay.ali.model.Ali;
import com.proper.enterprise.isj.pay.ali.repository.AliRepository;
import com.proper.enterprise.isj.pay.ali.service.AliService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付宝ServiceImpl
 */
@Service
public class AliServiceImpl implements AliService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AliServiceImpl.class);

    @Autowired
    AliRepository aliRepo;

    @Override
    public Ali save(Ali ali) {
        return aliRepo.save((AliEntity) ali);
    }

    @Override
    public void save(Ali... alis) {
        List<AliEntity> entities = new ArrayList<AliEntity>();
        for(Ali ali: alis) {
            entities.add((AliEntity) ali);
        }
        aliRepo.save((AliEntity) entities);
    }

    @Override
    public Ali findByOutTradeNo(String outTradeNo) {
        return aliRepo.findByOutTradeNo(outTradeNo);
    }

}

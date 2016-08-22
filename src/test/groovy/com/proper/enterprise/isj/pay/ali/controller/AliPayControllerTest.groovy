package com.proper.enterprise.isj.pay.ali.controller

import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.http.HttpStatus


class AliPayControllerTest extends AbstractTest{

    @Test
    public void prepay(){
        post('/pay/ali/prepayInfo', '{"orderNo":"hOpTChleZacRtNEpFVQEErZTlyNzwZyU", "subject": "挂号费", "body": "门诊挂号费用", "totalFee": "0.01"}', HttpStatus.CREATED)
    }
}

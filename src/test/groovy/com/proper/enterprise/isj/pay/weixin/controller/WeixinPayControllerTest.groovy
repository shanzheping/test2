package com.proper.enterprise.isj.pay.weixin.controller

import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.http.HttpStatus

class WeixinPayControllerTest extends AbstractTest {

    @Test
    public void prepay() {
        post('/pay/weixin/prepayInfo', '{"outTradeNo":"hOpTChleZacRtNEpFVQEErZTlyNzwZyU","body": "测试", "totalFee": 1}', HttpStatus.OK)
    }

}

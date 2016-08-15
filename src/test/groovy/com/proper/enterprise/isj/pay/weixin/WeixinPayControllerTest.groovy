package com.proper.enterprise.isj.pay.weixin

import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.http.HttpStatus


class WeixinPayControllerTest extends AbstractTest {

    @Test
    public void prepay() {
        get('/pay/weixin/prepayInfo', HttpStatus.OK)
    }

}

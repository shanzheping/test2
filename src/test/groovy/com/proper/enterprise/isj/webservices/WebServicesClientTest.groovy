package com.proper.enterprise.isj.webservices

import com.proper.enterprise.platform.core.utils.StringUtil
import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class WebServicesClientTest extends AbstractTest {

    @Autowired
    WebServicesClient client

    @Test
    public void test() {
        println "="*20
        println client.test()
        assert StringUtil.isNotNull(client.test())
        println "="*20
    }

}

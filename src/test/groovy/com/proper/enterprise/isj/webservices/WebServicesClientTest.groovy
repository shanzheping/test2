package com.proper.enterprise.isj.webservices

import com.proper.enterprise.platform.core.utils.StringUtil
import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class WebServicesClientTest extends AbstractTest {

    @Autowired
    WebServicesClient client

    @Test
    public void netTest() {
        def res = client.netTest('11', '192.168.1.1')
        println "=" * (res.length() + 4)
        println "|| $res ||"
        println "=" * (res.length() + 4)

        def xml = new XmlParser().parseText(res)
        assert StringUtil.isNotNull(xml.RETURN_CODE.text())
        assert StringUtil.isNotNull(xml.RETURN_MSG.text())
        assert xml.SIGN_TYPE.text() == 'MD5'
        assert StringUtil.isNotNull(xml.SIGN.text())
        assert StringUtil.isNotNull(xml.RES_ENCRYPTED.text())
    }

}

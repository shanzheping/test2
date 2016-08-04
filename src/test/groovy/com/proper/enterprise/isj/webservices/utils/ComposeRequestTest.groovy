package com.proper.enterprise.isj.webservices.utils
import com.proper.enterprise.isj.webservices.model.ReqModel
import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.oxm.Marshaller

import javax.xml.transform.stream.StreamResult

class ComposeRequestTest extends AbstractTest {

    @Autowired
    Marshaller marshaller

    @Test
    public void marshallReq() {
        def writer = new StringWriter()
        def m = new ReqModel()
        m.setFunCode('1111')
        m.setReqEncrypted([HOST_ID: '123', IP: '456'])
        marshaller.marshal(m, new StreamResult(writer))
        println writer.toString().replace('&lt;', '<').replace('&gt;', '>');

    }

}

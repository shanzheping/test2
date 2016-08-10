package com.proper.enterprise.isj.webservices.utils

import com.proper.enterprise.isj.webservices.model.req.ReqModel
import com.proper.enterprise.platform.core.utils.CipherUtil
import com.proper.enterprise.platform.core.utils.ConfCenter
import com.proper.enterprise.platform.core.utils.MD5Util
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
        def funCode = '1111'
        def userId = ConfCenter.get('isj.userId')
        def req = [HOST_ID: '123', IP: '456']

        def writer = new StringWriter()
        def m = new ReqModel()
        m.setFunCode(funCode)
        m.setUserId(userId)
        m.setReq(req)
        marshaller.marshal(m, new StreamResult(writer))

        def xml = new XmlParser().parseText(writer.toString())
        assert xml.FUN_CODE.text() == "<![CDATA[$funCode]]>"
        assert xml.USER_ID.text() == "<![CDATA[$userId]]>"
        assert xml.SIGN_TYPE.text() == "<![CDATA[MD5]]>"

        def reqEnc = '<REQ>'
        req.each { key, value ->
            reqEnc += "<$key><![CDATA[$value]]></$key>"
        }
        reqEnc += '</REQ>'
        CipherUtil aes = CipherUtil.getInstance(
                ConfCenter.get("isj.algorithm"),
                ConfCenter.get("isj.mode"),
                ConfCenter.get("isj.padding"),
                ConfCenter.get("isj.key"),
                Integer.parseInt(ConfCenter.get("isj.keySize")));
        reqEnc = aes.encrypt(reqEnc);
        assert xml.REQ_ENCRYPTED.text() == "<![CDATA[" + reqEnc + "]]>"

        def sign = "FUN_CODE=$funCode&REQ_ENCRYPTED=$reqEnc&USER_ID=$userId&KEY=${ConfCenter.get('isj.key')}"
        assert xml.SIGN.text() == "<![CDATA[${MD5Util.md5Hex(sign).toUpperCase()}]]>"
    }

}

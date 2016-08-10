package com.proper.enterprise.isj.webservices

import com.proper.enterprise.platform.core.utils.CipherUtil
import com.proper.enterprise.platform.core.utils.StringUtil
import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class WebServicesClientTest extends AbstractTest {

    @Autowired
    WebServicesClient client

    @Autowired
    CipherUtil aes;

    @Test
    public void reqNotTransferred() {
        def req = client.envelopReq('1001', [:])

        assert req.indexOf('&lt;') < 0
        assert req.indexOf('&gt;') < 0
    }

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

    @Test
    public void getRegInfo() {
        def resModel = client.getRegInfo('11', '-1', '123', new Date(), new Date())
        println resModel
    }

    @Test
    public void test() {
        println aes.decrypt('SQWUfujhjmzuuKE/ZebPtaKNRRn2hCiyNVuw4vpTptmijUUZ9oQosjVbsOL6U6bZJXw65UnnP7sAe9EOtXDqFmTEpLAZOo7QwtIOyZyA8iVZ/o8OSYwPSz9r3vo9hP9Foo1FGfaEKLI1W7Di+lOm2aKNRRn2hCiyNVuw4vpTptmiYHoaJvXEhiM8MHcK9aRZ')
        println aes.encrypt("""
<RES>
\t<HOS_ID></HOS_ID>
\t<DEPT_ID>100101</DEPT_ID>
\t<REG_DOCTOR_LIST>
\t\t<DOCTOR_ID></DOCTOR_ID>
\t\t<NAME>张为</NAME>
\t\t<JOB_TITLE>主治医师</JOB_TITLE>
\t\t<REG_LIST>
\t\t\t<REG_DATE>2014-10-12</REG_DATE>
\t\t\t<REG_WEEKDAY>星期日</REG_WEEKDAY>
\t\t\t<REG_TIME_LIST>
 \t\t\t\t<REG_ID>1005</REG_ID>
\t\t\t\t<TIME_FLAG>1</TIME_FLAG>
\t\t\t\t<REG_STATUS>1</REG_STATUS>
\t\t\t\t<TOTAL>15</TOTAL>
\t\t\t\t<OVER_COUNT>5</OVER_COUNT>
\t\t\t\t<REG_LEVEL>1</REG_LEVEL>
\t\t\t\t<REG_FEE>100</REG_FEE>
\t\t\t\t<TREAT_FEE>1000</TREAT_FEE>
\t\t\t\t<ISTIME>1</ISTIME>
\t\t\t</REG_TIME_LIST>
\t\t\t<REG_TIME_LIST>
\t\t\t\t<REG_ID>1005</REG_ID>
\t\t\t\t<TIME_FLAG>2</TIME_FLAG>
\t\t\t\t<REG_STATUS>1</REG_STATUS>
\t\t\t\t<TOTAL>15</TOTAL>
\t\t\t\t<OVER_COUNT>5</OVER_COUNT>
\t\t\t\t<REG_LEVEL>1</REG_LEVEL>
\t\t\t\t<REG_FEE>100</REG_FEE>
\t\t\t\t<TREAT_FEE>1000</TREAT_FEE>
\t\t\t\t<ISTIME>1</ISTIME>
\t\t\t</REG_TIME_LIST>
\t\t</REG_LIST>
\t\t<REG_LIST>
\t\t\t…
\t\t</REG_LIST>
\t</REG_DOCTOR_LIST>
</RES>
""")
    }

}

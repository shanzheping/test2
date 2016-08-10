package com.proper.enterprise.isj.webservices

import com.proper.enterprise.platform.core.utils.CipherUtil
import com.proper.enterprise.platform.core.utils.ConfCenter
import com.proper.enterprise.platform.core.utils.MD5Util
import com.proper.enterprise.platform.core.utils.StringUtil
import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import java.text.MessageFormat

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
        def resModel = client.netTest('11', '192.168.1.1')
        assert resModel.getReturnCode() != null
        assert StringUtil.isNotNull(resModel.returnMsg)
        assert resModel.getSignType() == 'MD5'
        assert StringUtil.isNotNull(resModel.getSign())
        assert StringUtil.isNotNull(resModel.getResEncrypted())
        assert resModel.getRes() != null
        assert resModel.getRes().sysDate != null
    }

    @Test
    public void getRegInfo() {
        def resModel = client.getRegInfo('11', '-1', '123', new Date(), new Date())
        def regInfo = resModel.getRes()
        assert regInfo != null
        assert regInfo.regDoctorList != null
        assert regInfo.regDoctorList[0].regList != null
        assert regInfo.regDoctorList[0].regList[0].regTimeList != null

        def regTime = regInfo.regDoctorList[0].regList[0].regTimeList[0]
        assert regTime.getTimeFlag() != null
        assert regTime.getRegStatus() != null
        assert regTime.getRegLevel() != null
        assert regTime.getIsTime() != null
    }

    @Test
    public void signTest() {
        def res = """<RES>
\t<HOS_ID>21010008</HOS_ID>
\t<DEPT_ID>100101</DEPT_ID>
\t<REG_DOCTOR_LIST>
\t\t<DOCTOR_ID>123</DOCTOR_ID>
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
\t</REG_DOCTOR_LIST>
</RES>"""
        def resEncrypted = aes.encrypt(res)

        println "RES_ENCRYPTED: ${resEncrypted}"

        String sign = MessageFormat.format(
                ConfCenter.get("isj.template.sign.res"),
                resEncrypted,
                '0',
                '交易成功',
                ConfCenter.get("isj.key"));
        println "SIGN: ${MD5Util.md5Hex(sign).toUpperCase()}"
    }

}

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
    public void getHosInfo() {
        def resModel = client.getHosInfo('11')
        def hosInfo = resModel.getRes()
        assert hosInfo.getLevel() != null
        println hosInfo.getLevel().name()
    }

    @Test
    public void signTest() {
        def res = """<RES>
\t<HOS_ID>1001</HOS_ID>
\t<NAME>广东省人民医院</NAME>
\t<SHORT_NAME>省人医</SHORT_NAME>
\t<ADDRESS>地址</ADDRESS>
\t<TEL>020-11231112</TEL>
\t<WEBSITE>http://www.xxx.com</WEBSITE>
\t<WEIBO></WEIBO>
\t<LEVEL>3</LEVEL>
\t<AREA></AREA>
\t<DESC></DESC>
\t<SPECIAL></SPECIAL>
\t<LONGITUDE></LONGITUDE>
\t<LATITUDE></LATITUDE>
\t<MAX_REG_DAYS>0</MAX_REG_DAYS>
\t<START_REG_TIME></START_REG_TIME>
\t<END_REG_TIME></END_REG_TIME>
\t<STOP_BOOK_TIMEA></STOP_BOOK_TIMEA>
\t<STOP_BOOK_TIMEP></STOP_BOOK_TIMEP>
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

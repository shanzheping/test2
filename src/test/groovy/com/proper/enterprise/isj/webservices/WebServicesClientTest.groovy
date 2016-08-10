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
    public void getDeptInfo() {
        def resModel = client.getDeptInfo('11', '-1')
        def deptInfo = resModel.getRes()
        assert deptInfo.deptList != null

        def dept = deptInfo.deptList[0]
        assert dept.getLevel() != null
        assert dept.getStatus() != null
    }

    @Test
    public void signTest() {
        def res = """<RES>
\t<HOS_ID></HOS_ID>
\t<DEPT_LIST>
\t\t<DEPT_ID>100101</DEPT_ID>
\t\t<DEPT_NAME>骨科</DEPT_NAME>
\t\t<PARENT_ID>-1</PARENT_ID>
\t\t<DESC></DESC>
\t\t<LEVEL>1</LEVEL>
\t\t<ADDRESS>门诊一楼</ADDRESS>
\t\t<EXPERTISE></EXPERTISE>
\t\t<STATUS>1</STATUS>
\t</DEPT_LIST>
\t<DEPT_LIST>
\t\t…
\t</DEPT_LIST>
</RES>
"""
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

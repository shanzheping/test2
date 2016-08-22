package com.proper.enterprise.isj.webservices

import com.proper.enterprise.isj.webservices.model.req.OrderRegReq
import com.proper.enterprise.platform.core.enums.WhetherType
import com.proper.enterprise.platform.core.utils.ConfCenter
import com.proper.enterprise.platform.core.utils.MD5Util
import com.proper.enterprise.platform.core.utils.StringUtil
import com.proper.enterprise.platform.core.utils.cipher.AES
import com.proper.enterprise.platform.test.AbstractTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import java.text.MessageFormat

class WebServicesClientTest extends AbstractTest {

    @Autowired
    WebServicesClient client

    @Autowired
    @Qualifier('hisAES')
    AES aes;

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
    public void orderReg() {
        def req = new OrderRegReq()
        def resModel = client.orderReg(req)
        def res = resModel.getRes()
        assert res.concessions != null
        assert res.isConcessions == WhetherType.YES || res.isConcessions == WhetherType.NO

        def concession = res.concessions[0]
        assert concession.concessionsFee >= 0
    }

    @Test
    public void signTest() {
        def res = """<RES>
\t<HOSP_PATIENT_ID>A001</HOSP_PATIENT_ID>
\t<HOSP_ORDER_ID>123</HOSP_ORDER_ID>
\t<HOSP_SERIAL_NUM></HOSP_SERIAL_NUM>
\t<HOSP_MEDICAL_NUM></HOSP_MEDICAL_NUM>
\t<HOSP_GETREG_DATE></HOSP_GETREG_DATE>
\t<HOSP_SEE_DOCT_ADDR></HOSP_SEE_DOCT_ADDR>
\t<HOSP_REMARK></HOSP_REMARK>
\t<HOSP_CARD_NO></HOSP_CARD_NO>
\t<IS_CONCESSIONS></IS_CONCESSIONS>
\t<CONCESSIONS>
\t\t<CONCESSIONS_FEE></CONCESSIONS_FEE>
\t\t<REAL_REG_FEE></REAL_REG_FEE>
\t\t<REAL_TREAT_FEE></REAL_TREAT_FEE>
\t\t<REAL_TOTAL_FEE></REAL_TOTAL_FEE>
\t\t<CONCESSIONS_TYPE></CONCESSIONS_TYPE>
\t</CONCESSIONS>
</RES>"""
        def resEncrypted = aes.encrypt(res)
        assert StringUtil.isNotNull(resEncrypted)
        println "RES_ENCRYPTED: ${resEncrypted}"

        String sign = MessageFormat.format(
                ConfCenter.get("isj.template.sign.res"),
                resEncrypted,
                '0',
                '交易成功',
                ConfCenter.get("isj.key"));
        assert StringUtil.isNotNull(sign);
        println "SIGN: ${MD5Util.md5Hex(sign).toUpperCase()}"
    }

}

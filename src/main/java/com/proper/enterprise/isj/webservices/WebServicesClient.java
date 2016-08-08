package com.proper.enterprise.isj.webservices;

import com.proper.enterprise.isj.webservices.model.RegOrder;
import com.proper.enterprise.isj.webservices.model.ReqModel;
import com.proper.enterprise.isj.webservices.service.RegSJService;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebServicesClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServicesClient.class);

    @Autowired
    RegSJService regSJService;

    @Autowired
    Marshaller marshaller;

    /**
     * 在调用其它接口之前用于测试目标服务网络是否通畅，服务是否处于工作状态、数据库是否处于连接状态
     *
     * @param hosId 医院ID
     * @param ip    请求IP
     * @return 返回系统时间，格式：YYYY-MM-DD HH24:MI:SS
     *
     * <RES>
     *   <SYSDATE>2014-10-01 20:22:10</SYSDATE>
     * </RES>
     *
     * @throws Exception
     */
    public String netTest(String hosId, String ip) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("IP", ip);
        return invokeWS("netTest", map);
    }

    private String invokeWS(String methodName, Map<String, String> param) throws Exception {
        Method method = RegSJService.class.getDeclaredMethod(methodName, String.class);
        return (String) method.invoke(regSJService,
                envelopReq(ConfCenter.get("isj.funCode." + methodName), param));
    }

    private String envelopReq(String funCode, Map<String, String> map) throws IOException {
        Assert.notNull(funCode, "FunCode should not null!");
        Writer writer = new StringWriter();
        ReqModel m = new ReqModel();
        m.setFunCode(funCode);
        m.setUserId(ConfCenter.get("isj.userId"));
        m.setReq(map);
        marshaller.marshal(m, new StreamResult(writer));

        String result = writer.toString();
        // 东软接口不识别转义后的形式，故需要将被 marshaller 自动转义的字符替换回去
        result = result.replace("&lt;", "<").replace("&gt;", ">");
        LOGGER.debug("Actual request after envelop is: {}", result);

        return result;
    }

    /**
     * 查询医院列表及单个医院的详细信息。
     * 需要获取医院基本信息时调用，平台可通过该接口获取医院的信息更新。
     *
     * @param hosId 医院ID
     * @return
     *
     * <RES>
     *   <HOS_ID>1001</HOS_ID>
     *   <NAME>广东省人民医院</NAME>
     *   <SHORT_NAME>省人医</SHORT_NAME>
     *   <ADDRESS>地址</ADDRESS>
     *   <TEL>020-11231112</TEL>
     *   <WEBSITE>http://www.xxx.com</WEBSITE>
     *   <WEIBO></WEIBO>
     *   <LEVEL>3</LEVEL>
     *   <AREA></AREA>
     *   <DESC></DESC>
     *   <SPECIAL></SPECIAL>
     *   <LONGITUDE></LONGITUDE>
     *   <LATITUDE></LATITUDE>
     *   <MAX_REG_DAYS>0</MAX_REG_DAYS>
     *   <START_REG_TIME></START_REG_TIME>
     *   <END_REG_TIME></END_REG_TIME>
     *   <STOP_BOOK_TIMEA></STOP_BOOK_TIMEA>
     *   <STOP_BOOK_TIMEP></STOP_BOOK_TIMEP>
     * </RES>
     *
     * @throws Exception
     */
    public String getHosInfo(String hosId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        return invokeWS("getHosInfo", map);
    }

    public String getDeptInfo(String hosId, String deptId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("DEPT_ID", deptId);
        return invokeWS("getDeptInfo", map);
    }

    public String getRegInfo(String hosId, String deptId, String doctorId, Date startDate, Date endDate) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("DEPT_ID", deptId);
        map.put("DOCTOR_ID", doctorId);
        map.put("START_DATE", DateUtil.toDateString(startDate));
        map.put("END_DATE", DateUtil.toDateString(endDate));
        return invokeWS("getRegInfo", map);
    }

    public String orderReg(RegOrder regOrder) throws Exception {
        return invokeWS("orderReg", regOrder.toParams());
    }

}

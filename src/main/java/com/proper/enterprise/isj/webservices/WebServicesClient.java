package com.proper.enterprise.isj.webservices;

import com.proper.enterprise.isj.webservices.model.ReqModel;
import com.proper.enterprise.isj.webservices.service.RegSJService;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
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

    public String netTest(String hosId, String ip) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("IP", ip);
        return invokeWS("netTest", map);
    }

    private String invokeWS(String methodName, Map<String, String> param) throws Exception {
        Method method = RegSJService.class.getDeclaredMethod(methodName, String.class);
        return (String) method.invoke(regSJService,
                envelopReq(ConfCenter.get("isj.funCode." + method), param));
    }

    private String envelopReq(String funCode, Map<String, String> map) throws IOException {
        StringWriter writer = new StringWriter();
        ReqModel m = new ReqModel();
        m.setFunCode(funCode);
        m.setUserId(ConfCenter.get("isj.userId"));
        m.setReqEncrypted(map);
        marshaller.marshal(m, new StreamResult(writer));

        String result = writer.toString();
        LOGGER.debug("Actual request after envelop is: {}", result);

        return result;
    }

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

    public String orderReg() throws Exception {
        Map<String, String> map = new HashMap<>();
        // TODO
        return invokeWS("orderReg", map);
    }

}

package com.proper.enterprise.isj.webservices;

import com.proper.enterprise.isj.webservices.model.ReqModel;
import com.proper.enterprise.isj.webservices.service.RegSJService;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebServicesClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServicesClient.class);

    @Autowired
    RegSJService regSJService;

    @Autowired
    Marshaller marshaller;

    public String netTest(String hosId, String ip) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("IP", ip);
        return regSJService.netTest(envelopReq(ConfCenter.get("isj.funCode.netTest"), map));
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

}

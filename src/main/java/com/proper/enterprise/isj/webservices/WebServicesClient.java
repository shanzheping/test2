package com.proper.enterprise.isj.webservices;

import com.proper.enterprise.isj.webservices.model.ReqModel;
import com.proper.enterprise.isj.webservices.service.RegSJService;
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

    public String test() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", "11");
        map.put("IP", "192.168.1.103");
        return regSJService.netTest(envelopReq(map));
    }

    private String envelopReq(Map<String, String> map) throws IOException {
//        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//            "<ROOT>\n" +
//            "<FUN_CODE><![CDATA[1001]]></FUN_CODE>\n" +
//            "<USER_ID><![CDATA[000000]]></USER_ID>\n" +
//            "<SIGN_TYPE><![CDATA[MD5]]></SIGN_TYPE>\n" +
//            "<SIGN><![CDATA[sdj1o21l12j01jaa]]></SIGN>\n" +
//            "<REQ_ENCRYPTED><![CDATA[ADqweJsq34ASODU912IJ312AWE1LJ123J1291281829FB12890102s90ad0180D1231S]]></REQ_ENCRYPTED>\n" +
//            "</ROOT>";

        StringWriter writer = new StringWriter();
        ReqModel m = new ReqModel();
        m.setFunCode("1001");
        m.setUserId("DEFAULT_USER");
        m.setReqEncrypted(map);
        marshaller.marshal(m, new StreamResult(writer));

        String result = writer.toString();
        LOGGER.debug("Actual request after envelop is: {}", result);

        return result;
    }

}

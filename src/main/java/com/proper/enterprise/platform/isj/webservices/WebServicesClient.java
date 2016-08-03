package com.proper.enterprise.platform.isj.webservices;

import com.proper.enterprise.platform.isj.webservices.service.RegSJService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebServicesClient {

    @Autowired
    RegSJService regSJService;

    public String test() {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", "11");
        map.put("IP", "192.168.1.103");
        return regSJService.netTest(envelopReq(map));
    }

    private String envelopReq(Map<String, String> map) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<ROOT>\n" +
            "<FUN_CODE><![CDATA[1001]]></FUN_CODE>\n" +
            "<USER_ID><![CDATA[000000]]></USER_ID>\n" +
            "<SIGN_TYPE><![CDATA[MD5]]></SIGN_TYPE>\n" +
            "<SIGN><![CDATA[sdj1o21l12j01jaa]]></SIGN>\n" +
            "<REQ_ENCRYPTED><![CDATA[ADqweJsq34ASODU912IJ312AWE1LJ123J1291281829FB12890102s90ad0180D1231S]]></REQ_ENCRYPTED>\n" +
            "</ROOT>";
    }

}

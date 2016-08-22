package com.proper.enterprise.isj.webservices.utils;

import com.proper.enterprise.platform.core.utils.ConfCenter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.MessageFormat;
import java.util.Map;

public class ReqEncryptedAdapter extends XmlAdapter<String, Map<String, String>> {

    private static final com.proper.enterprise.platform.core.utils.cipher.AES AES;

    static {
        AES = new com.proper.enterprise.platform.core.utils.cipher.AES(
                ConfCenter.get("isj.his.aes.mode"),
                ConfCenter.get("isj.his.aes.padding"),
                ConfCenter.get("isj.his.aes.key"));
    }

    @Override
    public Map<String, String> unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(Map<String, String> v) throws Exception {
        return marshal(v, true);
    }

    public String marshal(Map<String, String> v, boolean needCDATA) throws Exception {
        StringBuilder sb = new StringBuilder("<REQ>");
        for(Map.Entry<String, String> entry : v.entrySet()) {
            sb.append(MessageFormat.format(ConfCenter.get("isj.his.template.req"), entry.getKey(), entry.getValue()));
        }
        sb.append("</REQ>");
        String result = AES.encrypt(sb.toString());
        return needCDATA ? CDATAAdapter.wrapCDATA(result) : result;
    }

}

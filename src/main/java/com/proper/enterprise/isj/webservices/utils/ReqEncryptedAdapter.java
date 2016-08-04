package com.proper.enterprise.isj.webservices.utils;

import com.proper.enterprise.platform.core.utils.CipherUtil;
import com.proper.enterprise.platform.core.utils.ConfCenter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.MessageFormat;
import java.util.Map;

public class ReqEncryptedAdapter extends XmlAdapter<String, Map<String, String>> {

    @Override
    public Map<String, String> unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(Map<String, String> v) throws Exception {
        StringBuilder sb = new StringBuilder("<REQ>");
        for(Map.Entry<String, String> entry : v.entrySet()) {
            sb.append(MessageFormat.format(MsgTemplates.REQ, entry.getKey(), entry.getValue()));
        }
        sb.append("</REQ>");

        CipherUtil aes = CipherUtil.getInstance(
                ConfCenter.get("isj.algorithm"),
                ConfCenter.get("isj.mode"),
                ConfCenter.get("isj.padding"),
                ConfCenter.get("isj.key"),
                Integer.parseInt(ConfCenter.get("isj.keySize")));

        return aes.encrypt(sb.toString());
    }

}

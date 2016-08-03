package com.proper.enterprise.isj.webservices.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.MessageFormat;
import java.util.Map;

public class ReqEncryptedAdapter extends XmlAdapter<String, Map<String, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReqEncryptedAdapter.class);

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

        LOGGER.debug("Content before encrypt: {}", sb);


        return sb.toString();
    }

}

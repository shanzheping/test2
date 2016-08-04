package com.proper.enterprise.isj.webservices.utils;

import com.proper.enterprise.isj.webservices.model.ReqModel;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.MD5Util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.MessageFormat;

public class SignAdapter extends XmlAdapter<String, ReqModel> {

    @Override
    public ReqModel unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(ReqModel v) throws Exception {
        String sign = MessageFormat.format(ConfCenter.get("isj.template.sign.req"),
                v.getFunCode(),
                v.getReqEncrypted(),
                v.getUserId(),
                ConfCenter.get("isj.key"));
        return "<![CDATA[" + MD5Util.md5Hex(sign) + "]]>";
    }

}

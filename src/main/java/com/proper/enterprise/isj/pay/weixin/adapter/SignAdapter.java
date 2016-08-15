package com.proper.enterprise.isj.pay.weixin.adapter;

import com.proper.enterprise.isj.pay.weixin.WeixinConstants;
import com.proper.enterprise.isj.pay.weixin.model.UnifiedOrderReq;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.MD5Util;
import com.proper.enterprise.platform.core.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

public class SignAdapter extends XmlAdapter<String, UnifiedOrderReq> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignAdapter.class);

    @Override
    public UnifiedOrderReq unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(UnifiedOrderReq v) throws Exception {
        Field[] fields = UnifiedOrderReq.class.getDeclaredFields();
        Set<String> set = new TreeSet<>();
        for (Field field : fields) {
            if (!field.getName().equals("sign")) {
                set.add(field.getName());
            }
        }

        StringBuilder sb = new StringBuilder();
        Object value;
        for (String fieldName : set) {
            value = UnifiedOrderReq.class.getMethod("get" + StringUtil.capitalize(fieldName)).invoke(v);
            if (value != null) {
                sb.append(StringUtil.camelToSnake(fieldName)).append("=").append(value).append("&");
            }
        }
        sb.append("key=" + WeixinConstants.API_KEY);

        String sign = sb.toString();
        LOGGER.debug("Sign before MD5: {}",
                StringUtil.abbreviate(sign,
                        Integer.parseInt(ConfCenter.get("isj.abbreviate.maxWidth"))));

        return MD5Util.md5Hex(sign).toUpperCase();
    }

}

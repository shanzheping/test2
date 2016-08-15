package com.proper.enterprise.isj.pay.weixin;

import com.proper.enterprise.isj.pay.weixin.model.UnifiedOrderReq;
import com.proper.enterprise.isj.pay.weixin.model.UnifiedOrderRes;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.HttpClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/pay/weixin")
public class WeixinPayController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayController.class);

    @Autowired
    Marshaller marshaller;

    @Autowired
    Unmarshaller unmarshaller;

    @RequestMapping(value="/prepayInfo")
    public ResponseEntity<Map<String, String>> getWXPrepayInfo(HttpServletRequest request, UnifiedOrderReq uoReq) throws IOException {
        uoReq.setNonceStr(RandomStringUtils.randomAlphabetic(WeixinConstants.RANDOM_LEN));
        uoReq.setOutTradeNo(RandomStringUtils.randomAlphabetic(16)); // TODO
        uoReq.setSpbillCreateIp(request.getRemoteAddr());

        StringWriter writer = new StringWriter();
        marshaller.marshal(uoReq, new StreamResult(writer));
        String requestXML = writer.toString();

        ResponseEntity<String> response = HttpClient.post(
                WeixinConstants.UNIFIED_ORDER_URL,
                MediaType.APPLICATION_FORM_URLENCODED,
                requestXML);
        UnifiedOrderRes res = (UnifiedOrderRes) unmarshaller.unmarshal(new StreamSource(new StringReader(response.getBody())));

        Map<String, String> retObj = new HashMap<>();

        LOGGER.info("return_msg:" + res.getReturnMsg());

        // 以下字段在return_code为SUCCESS的时候有返回
        if("SUCCESS".equals(res.getReturnCode()) && "SUCCESS".equals(res.getResultCode())) {
            LOGGER.info("result_code:SUCCESS");
            // 返回给请求客户端处理结果
            retObj.put("result_code", "0");
            // 返回给请求客户端处理结果消息
            retObj.put("result_message", "SUCCESS");
            // 返回给请求客户端交易类型
            retObj.put("trade_type", res.getTradeType());

            //-------------返回给客户端调用支付接口需要的参数-----------------------
            // 应用ID
            retObj.put("appid", res.getAppId());
            // 商户号
            retObj.put("partnerid", res.getMchId());
            // 预支付交易会话ID
            retObj.put("prepayid", res.getPrepayId());
            // 扩展字段
            retObj.put("package", "Sign=WXPay");
            // 随机字符串
            retObj.put("noncestr", res.getNonceStr());
            // 时间戳
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            retObj.put("timestamp", timestamp);
            // 签名
            retObj.put("sign", res.getSign());
        }
        return new ResponseEntity<>(retObj, HttpStatus.OK);
    }

}

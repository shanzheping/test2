package com.proper.enterprise.isj.pay.weixin.controller;

import com.proper.enterprise.isj.order.model.Order;
import com.proper.enterprise.isj.order.service.OrderService;
import com.proper.enterprise.isj.pay.weixin.constants.WeixinConstants;
import com.proper.enterprise.isj.pay.weixin.adapter.SignAdapter;
import com.proper.enterprise.isj.pay.weixin.entity.WeixinEntity;
import com.proper.enterprise.isj.pay.weixin.model.*;
import com.proper.enterprise.isj.pay.weixin.service.WeixinService;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.HttpClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
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

    @Autowired
    WeixinService weixinService;

    @Autowired
    OrderService orderService;

    /**
     * 获取微信预支付ID信息
     *
     * @param uoReq
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/prepayInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> getWXPrepayInfo(@RequestBody UnifiedOrderReq uoReq, HttpServletRequest request) throws IOException {
        uoReq.setNonceStr(RandomStringUtils.randomAlphabetic(WeixinConstants.RANDOM_LEN));
        //uoReq.setOutTradeNo(RandomStringUtils.randomAlphabetic(32)); // TODO
        uoReq.setSpbillCreateIp(request.getRemoteAddr());

        StringWriter writer = new StringWriter();
        marshaller.marshal(uoReq, new StreamResult(writer));
        String requestXML = writer.toString();

        LOGGER.info("requestXML:" + requestXML);

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
            retObj.put("resultCode", "0");
            // 返回给请求客户端处理结果消息
            retObj.put("resultMessage", "SUCCESS");
            // 返回给请求客户端交易类型
            retObj.put("tradeType", res.getTradeType());

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

            // 获取预支付ID信息失败
        } else {
            // 返回给请求客户端处理结果
            retObj.put("resultCode", "-1");
            // 返回给请求客户端处理结果消息
            retObj.put("resultMessage", "Fail");
        }
        return new ResponseEntity<>(retObj, HttpStatus.OK);
    }

    /**
     * 接收微信异步通知结果
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/noticeInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<String> receiveWeixinNoticeInfo(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        LOGGER.info("-------------微信异步通知---------------");
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息

        // 返回给微信服务器的微信异步通知结果
        boolean ret = false;

        UnifiedNoticeRes res = (UnifiedNoticeRes) unmarshaller.unmarshal(new StreamSource(new StringReader(result)));

        LOGGER.info("notice_msg:" + res.getReturnMsg());

        //进行签名验证操作
        SignAdapter signAdapter = new SignAdapter();
        if(res.getSign().equals(signAdapter.marshalObject(res, UnifiedNoticeRes.class))) {
            LOGGER.info("sign_verify:SUCCESS");
            // 以下字段在return_code为SUCCESS的时候有返回
            if("SUCCESS".equalsIgnoreCase(res.getReturnCode()) && "SUCCESS".equalsIgnoreCase(res.getResultCode())) {
                LOGGER.info("result_code:SUCCESS");
                // 保存微信异步通知信息
                // 取得内部订单号
                String orderNo = res.getOutTradeNo();
                // 查询订单
                Order orderinfo = orderService.findByOrderNo(orderNo);
                // 存在订单
                if(orderinfo != null) {
                    // 没有处理过订单
                    if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.unconfirmpay")) {
                        // 保存微信异步通知信息
                        WeixinEntity weixinInfo = getWeixinNoticeInfo(res);
                        // 更新订单状态
                        orderinfo.setPaymentStatus(ConfCenter.getInt("isj.pay.paystatus.payed"));
                        orderService.save(orderinfo);
                        // 保存微信异步通知信息
                        weixinService.save(weixinInfo);
                        ret = true;
                        // 已经成功处理过的订单
                    } else if(orderinfo.getPaymentStatus() == ConfCenter.getInt("isj.pay.paystatus.payed")) {
                        ret = true;
                    }
                }
            }
        }

        UnifiedNoticeReq responseWeixin = new UnifiedNoticeReq();
        if(ret) {
            responseWeixin.setReturnCode("SUCCESS");
            responseWeixin.setReturnMsg("OK");
        } else {
            responseWeixin.setReturnCode("FAIL");
            responseWeixin.setReturnMsg("ERROR");
        }
        StringWriter writer = new StringWriter();
        marshaller.marshal(responseWeixin, new StreamResult(writer));
        String requestXML = writer.toString();
        return new ResponseEntity<>(requestXML, HttpStatus.OK);//TODO 待确认
    }

    /**
     * 保存微信异步通知信息到数据库
     *
     * @param weixinNoticeInfo
     */
    private WeixinEntity getWeixinNoticeInfo(UnifiedNoticeRes weixinNoticeInfo) {
        WeixinEntity weixinInfo = new WeixinEntity();
        BeanUtils.copyProperties(weixinNoticeInfo, weixinInfo);
        weixinInfo.setIsdel(ConfCenter.getInt("isj.pay.isdel.nomarl"));
        return weixinInfo;
    }

}

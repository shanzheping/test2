package com.proper.enterprise.isj.pay.weixin;

import com.proper.enterprise.isj.pay.PayConstants;
import com.proper.enterprise.isj.pay.weixin.model.UnifiedOrderRes;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping(path = "/pay/weixin")
public class WeixinPayController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayController.class);

    @Autowired
    Unmarshaller unmarshaller;

    @RequestMapping(value="/prepayInfo")
    public ResponseEntity<Map<String, String>> getWXPrepayInfo(HttpServletRequest request) {
        Map<String, String> retObj = new HashMap<>();
        try{

            //------------------------取得获取预支付ID所必须的支付信息------------------------------
            // 设备号 终端设备号(门店号或收银设备ID)，默认请传"WEB"
            String device_info = request.getParameter("device_info");
            // 商品描述 必填 —需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
            String body = request.getParameter("body");
            // 商品详情
            String detail = request.getParameter("detail");
            // 附加数据
            String attach = request.getParameter("attach");
            // 总金额 必填 订单总金额，单位为分
            String total_fee = request.getParameter("total_fee");
            //----------------------------------------------------------------------------

            //------------------------调用统一下单API获取预支付ID------------------------------
            SortedMap<String, String> parameters = new TreeMap<String, String>();
            // 应用ID 必填
            parameters.put("appid", ConfCenter.get("weixin_appid"));
            // 商户号 必填
            parameters.put("mch_id", ConfCenter.get("weixin_mch_id"));
            // 设备号 终端设备号(门店号或收银设备ID)，默认请传"WEB"
            if(StringUtil.isNotNull(device_info)){
                parameters.put("device_info", device_info);
            }
            // 商品描述 必填
            parameters.put("body", body);
            // 随机字符串 必填
            String nonce_str = RandomStringUtils.randomAlphabetic(Integer.parseInt(ConfCenter.get("isj.pay.wx.randomLen", "16")));
            parameters.put("nonce_str", nonce_str);
            // 商品详情
            if(StringUtil.isNotNull(detail)){
                parameters.put("detail", detail);
            }
            // 附加数据
            if(StringUtil.isNotNull(attach)){
                parameters.put("attach", attach);
            }
            // 商户订单号 必填
            String out_trade_no = RandomStringUtils.randomAlphabetic(16);// TODO
            parameters.put("out_trade_no", out_trade_no);
            // 货币类型
            String fee_type = "CNY";
            parameters.put("fee_type", fee_type);
            // 总金额 必填 订单总金额，单位为分
            parameters.put("total_fee", total_fee);
            // 终端IP 必填
            String spbill_create_ip = request.getRemoteAddr();
            parameters.put("spbill_create_ip", spbill_create_ip);
            // 交易起始时间_订单生成时间，格式为yyyyMMddHHmmss
            String time_start = "";
            // 交易结束时间_订单失效时间，格式为yyyyMMddHHmmss(注意：最短失效时间间隔必须大于5分钟)
            String time_expire = "";
            // 商品标记
            String goods_tag = "";
            // 通知地址 必填  接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
            String notify_url = "http://weixin.propersoft.cn/iosj/api/receiveWXpayNotice";//TODO
            parameters.put("notify_url", notify_url);
            // 交易类型 必填
            String trade_type = "APP";
            parameters.put("trade_type", trade_type);
            // 指定支付方式 no_credit--指定不能使用信用卡支付
            String limit_pay = "";

            // 签名 必填
            String sign = Collector.createSign(parameters);
            parameters.put("sign", sign);

            String requestXML = Collector.getRequestXml(parameters);
            String result = Collector.httpsRequest(PayConstants.UNIFIED_ORDER_URL, "POST", requestXML);

            UnifiedOrderRes res = (UnifiedOrderRes) unmarshaller.unmarshal(new StreamSource(new StringReader(requestXML)));
            LOGGER.info("return_msg:" + res.getReturnMsg());

            // 以下字段在return_code为SUCCESS的时候有返回
            if("SUCCESS".equals(res.getReturnCode())) {
                LOGGER.info("return_code:SUCCESS");
                // 应用APPID 必填
                String retAppid = res.getAppId();
                // 商户号 必填
                String retMch_id = res.getMchId();
                // 设备号
//				String retDevice_info = map.get("device_info");
                // 随机字符串 必填
                String retNonce_str = res.getNonceStr();
                // 签名 必填
                String retSign = res.getSign();
                // 业务结果 必填
                String retResult_code = res.getResultCode();
                // 错误代码
//				String retErr_code = map.get("err_code");
                // 错误代码描述
//				String retErr_code_des = map.get("err_code_des");

                // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
                if("SUCCESS".equals(retResult_code)){
                    LOGGER.info("result_code:SUCCESS");
                    // 交易类型 必填
                    String retTrade_type = res.getTradeType();
                    // 预支付交易会话标识
                    String retPrepay_id = res.getPrepayId();

                    // 返回给请求客户端处理结果
                    retObj.put("result_code", "0");
                    // 返回给请求客户端处理结果消息
                    retObj.put("result_message", "SUCCESS");
                    // 返回给请求客户端交易类型
                    retObj.put("trade_type", retTrade_type);

                    //-------------返回给客户端调用支付接口需要的参数-----------------------
                    // 应用ID
                    retObj.put("appid", retAppid);
                    // 商户号
                    retObj.put("partnerid", retMch_id);
                    // 预支付交易会话ID
                    retObj.put("prepayid", retPrepay_id);
                    // 扩展字段
                    retObj.put("package", "Sign=WXPay");
                    // 随机字符串
                    retObj.put("noncestr", retNonce_str);
                    // 时间戳
                    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                    retObj.put("timestamp", timestamp);
                    // 签名
                    retObj.put("sign", retSign);
                    //-----------------------------------------------------------
                }
            }
        } catch (Exception e) {
            LOGGER.error("ERROR", e);
            // 返回给请求客户端处理结果
            retObj.put("result_code", "-1");
            // 返回给请求客户端处理结果消息
            retObj.put("result_message", "ERROR");
        } finally {
            //response.getWriter().write(retObj.toString());
            return new ResponseEntity<Map<String, String>>(retObj, HttpStatus.OK);
        }
    }

}

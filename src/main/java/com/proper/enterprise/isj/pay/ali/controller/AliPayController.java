package com.proper.enterprise.isj.pay.ali.controller;

import com.proper.enterprise.isj.order.model.Order;
import com.proper.enterprise.isj.order.service.OrderService;
import com.proper.enterprise.isj.pay.ali.constants.AliConstants;
import com.proper.enterprise.isj.pay.ali.entity.AliEntity;
import com.proper.enterprise.isj.pay.ali.model.UnifiedOrderReq;
import com.proper.enterprise.isj.pay.ali.service.AliService;
import com.proper.enterprise.platform.core.PEPConstants;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.DateUtil;
import com.proper.enterprise.platform.core.utils.HttpClient;
import com.proper.enterprise.platform.core.utils.StringUtil;
import com.proper.enterprise.platform.core.utils.cipher.RSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping(value="/pay/ali")
public class AliPayController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    AliService aliService;

    @Autowired
    OrderService orderService;

    @Autowired
    @Qualifier("aliRSA")
    RSA rsa;

    @PostMapping(value="/prepayInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> getPrepayinfo(@RequestBody UnifiedOrderReq uoReq) throws Exception {

        // 取得订单信息
        String orderInfo = getOrderInfo(uoReq);
        // 获取秘钥
        String privateKey = AliConstants.ALI_PAY_RSA_PRIVATE;
        // 对订单信息进行签名
        String sign = rsa.sign(orderInfo, privateKey);
        // TODO 是否有必要做 URL encode?
        sign = URLEncoder.encode(sign, PEPConstants.DEFAULT_CHARSET.name());
        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
        // 定义返回参数
        Map<String, String> retObj = new HashMap<>();
        // TODO 响应能否定义一个统一的对象？
        //-------------返回给客户端调用支付接口需要的参数-----------------------
        // 返回给请求客户端处理结果
        retObj.put("resultCode", "0");
        // 返回给请求客户端处理结果消息
        retObj.put("resultMessage", "SUCCESS");
        // payInfo
        retObj.put("pay_info", payInfo);
        // 秘钥
        retObj.put("sign", privateKey);
        LOGGER.info("retObj:" + retObj);
        //-----------------------------------------------------------
        return responseOfPost(retObj);
    }

    @PostMapping(value="/noticeInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // TODO requestBody 映射成对象使用起来会更方便
    public void noticeInof(HttpServletRequest request) throws Exception {
        LOGGER.info("-----------支付宝异步通知---------------------");

        // 返回给支付宝服务器的异步通知结果
        boolean ret = false;

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        LOGGER.info("notice_msg:" + requestParams);
        for(Map.Entry<String, String[]> entry: requestParams.entrySet()) {
            String name = entry.getKey();
            params.put(name, StringUtil.join(entry.getValue(), ","));
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        // TODO 下面这三个字符集转换应该都不需要
        String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //支付宝交易号
        // String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        if(verify(params)) {//验证成功
            // 取得交易状态
            if(tradeStatus.equals(AliConstants.ALI_PAY_NOTICE_TARDESTATUS_TRADE_FINISHED)){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                // 查询订单
                // TODO 应该在 service 中写更多的逻辑，而不是在 controller 中
                Order orderinfo = orderService.findByOrderNo(outTradeNo);
                // 查询订单号是否已经处理过了
                // 存在订单
                if(orderinfo != null) {
                    // 没有处理过订单
                    if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.unconfirmpay")) {
                        // 保存支付宝异步通知信息
                        AliEntity aliInfo = getAliNoticeInfo(params);
                        // 更新订单状态
                        orderinfo.setPaymentStatus(ConfCenter.getInt("isj.pay.paystatus.payed"));
                        orderService.save(orderinfo);
                        // 保存支付宝异步通知信息
                        aliService.save(aliInfo);
                        ret = true;
                        // 已经成功处理过的订单
                    } else if(orderinfo.getPaymentStatus() == ConfCenter.getInt("isj.pay.paystatus.payed")) {
                        ret = true;
                    }
                }

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            } else if(tradeStatus.equals(AliConstants.ALI_PAY_NOTICE_TARDESTATUS_TRADE_SUCCESS)) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                // 查询订单
                Order orderinfo = orderService.findByOrderNo(outTradeNo);
                // 查询订单号是否已经处理过了
                // 存在订单
                if(orderinfo != null) {
                    // 没有处理过订单
                    if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.unconfirmpay")) {
                        // 保存支付宝异步通知信息
                        AliEntity aliInfo = getAliNoticeInfo(params);
                        // 更新订单状态
                        orderinfo.setPaymentStatus(ConfCenter.getInt("isj.pay.paystatus.payed"));
                        orderService.save(orderinfo);
                        // 保存支付宝异步通知信息
                        aliService.save(aliInfo);
                        ret = true;
                        // 已经成功处理过的订单
                    } else if(orderinfo.getPaymentStatus() == ConfCenter.getInt("isj.pay.paystatus.payed")) {
                        ret = true;
                    }
                }

                //注意：
                //该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
                //付款完成后，支付宝系统发送该交易状态通知
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            }
        }

        // TODO 此处的 system.out 应该没什么意义
        if(ret) {
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAIL");
        }
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(UnifiedOrderReq uoReq) {

        // TODO 这个是随意拼接？不应该用 String
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + AliConstants.ALI_PAY_PARTNER_ID + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + AliConstants.ALI_PAY_SELLER_ID + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + uoReq.getOrderNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + uoReq.getSubject() + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + uoReq.getBody() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + uoReq.getTotalFee() + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + AliConstants.ALI_PAY_NOTICE_URL + "\""; // TODO

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    private boolean verify(Map<String, String> params) throws Exception {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "false";
        if(params.get("notify_id") != null) {
            String notifyId = params.get("notify_id");
            responseTxt = verifyResponse(notifyId);
        }
        String sign = "";
        if(params.get("sign") != null) {
            sign = params.get("sign");
        }
        boolean isSign = getSignVeryfy(params, sign);

        //写日志记录（若要调试，请取消下面两行注释）
        //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
        //AlipayCore.logResult(sWord);

        return isSign && responseTxt.equals("true");
    }

    /**
     * 获取远程服务器ATN结果,验证返回URL
     * @param notifyId 通知校验ID
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private String verifyResponse(String notifyId) throws IOException {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

        String partner = AliConstants.ALI_PAY_PARTNER_ID;
        String veryfyUrl = AliConstants.ALI_PAY_NOTICE_HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notifyId;

        return checkUrl(veryfyUrl);
    }

    /**
     * 获取远程服务器ATN结果
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private String checkUrl(String urlvalue) throws IOException {
        return HttpClient.get(urlvalue).getBody();
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
    private boolean getSignVeryfy(Map<String, String> params, String sign) throws Exception {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(params);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        //获得签名验证结果
        return AliConstants.ALI_PAY_SIGN_TYPE.equals("RSA")
                && rsa.verifySign(preSignStr, sign, AliConstants.ALI_PAY_RSA_PUBLIC);
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (Map.Entry entry : sArray.entrySet()) {
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr.append(key);
                prestr.append("=");
                prestr.append(value);
            } else {
                prestr.append(key);
                prestr.append("=");
                prestr.append(value);
                prestr.append("&");
            }
        }

        return prestr.toString();
    }

    /**
     * 创建支付宝支付信息Entity
     *
     * @param params 参数
     * @return alipayinfo 支付信息
     * @throws Exception
     */
    private AliEntity getAliNoticeInfo(Map<String, String> params) throws Exception {
        //支付宝支付信息Bean
        AliEntity alipayinfo = new AliEntity();
        //通知时间
        if(StringUtil.isNotNull(params.get("notify_time"))) {
            alipayinfo.setNotifyTime(DateUtil.toDate(params.get("notify_time")));
        }

        LOGGER.info("[notify_time] = [" + params.get("notify_time") + "]");
        //通知类型
        alipayinfo.setNotifyType(params.get("notify_type"));
        LOGGER.info("[notify_type] = [" + params.get("notify_type") + "]");
        //通知校验id
        alipayinfo.setNotifyId(params.get("notify_id"));
        LOGGER.info("[notify_id] = [" + params.get("notify_id") + "]");
        //签名方式
        alipayinfo.setSignType(params.get("sign_type"));
        LOGGER.info("[sign_type] = [" + params.get("sign_type") + "]");
        //签名
        alipayinfo.setSign(params.get("sign"));
        LOGGER.info("[sign] = [" + params.get("sign") + "]");
        //商户网站唯一订单号 (orderid原样返回)
        alipayinfo.setOutTradeNo(params.get("out_trade_no"));
        LOGGER.info("[out_trade_no] = [" + params.get("out_trade_no") + "]");
        //商品名称(原样返回)
        alipayinfo.setSubject(params.get("subject"));
        LOGGER.info("[subject] = [" + params.get("subject") + "]");
        //支付类型
        alipayinfo.setPaymentType(params.get("payment_type"));
        LOGGER.info("[payment_type] = [" + params.get("payment_type") + "]");
        //支付宝交易号
        alipayinfo.setTradeNo(params.get("trade_no"));
        LOGGER.info("[trade_no] = [" + params.get("trade_no") + "]");
        //交易状态
        //0 : 未知
        if(StringUtil.isNull(params.get("trade_status"))) {
            alipayinfo.setTradeStatus(AliConstants.ALI_PAY_NOTICE_TARDESTATUS_UNKONWN);
        } else {
            alipayinfo.setTradeStatus(params.get("trade_status"));
        }
        LOGGER.info("[trade_status] = [" + params.get("trade_status") + "]");
        //卖家支付宝用户号
        alipayinfo.setSellerId(params.get("seller_id"));
        LOGGER.info("[seller_id] = [" + params.get("seller_id") + "]");
        //卖家支付宝账号
        alipayinfo.setSellerEmail(params.get("seller_email"));
        LOGGER.info("[seller_email] = [" + params.get("seller_email") + "]");
        //买家支付宝用户号
        alipayinfo.setBuyerId(params.get("buyer_id"));
        LOGGER.info("[buyer_id] = [" + params.get("buyer_id") + "]");
        //买家支付宝账号
        alipayinfo.setBuyerEmail(params.get("buyer_email"));
        LOGGER.info("[buyer_email] = [" + params.get("buyer_email") + "]");
        //交易金额
        if(StringUtil.isNull(params.get("total_fee"))) {
            alipayinfo.setTotalFee("0");
        } else {
            alipayinfo.setTotalFee(params.get("total_fee"));
        }
        LOGGER.info("[total_fee] = [" + params.get("total_fee") + "]");
        //购买数量
        if(StringUtil.isNull(params.get("quantity"))) {
            alipayinfo.setQuantity("1");
        } else {
            alipayinfo.setQuantity(params.get("quantity"));
        }
        LOGGER.info("[quantity] = [" + params.get("quantity") + "]");
        //商品单价
        if(StringUtil.isNull(params.get("price"))) {
            alipayinfo.setPrice("0");
        } else {
            alipayinfo.setPrice(params.get("price"));
        }
        LOGGER.info("[price] = [" + params.get("price") + "]");
        //商品描述
        alipayinfo.setBody(params.get("body"));
        LOGGER.info("[body] = [" + params.get("body") + "]");
        //交易创建时间
        if(StringUtil.isNotNull(params.get("gmt_create"))) {
            alipayinfo.setGmtCreate(DateUtil.toDate(params.get("gmt_create")));
        }
        LOGGER.info("[gmt_create] = [" + params.get("gmt_create") + "]");
        //交易付款时间
        if(StringUtil.isNotNull(params.get("gmt_payment"))) {
            alipayinfo.setGmtPayment(DateUtil.toDate(params.get("gmt_payment")));
        }
        LOGGER.info("[gmt_payment] = [" + params.get("gmt_payment") + "]");
        //是否调整总价
        alipayinfo.setIsTotalFeeAdjust(params.get("is_total_fee_adjust"));
        LOGGER.info("[is_total_fee_adjust] = [" + params.get("is_total_fee_adjust") + "]");
        //是否使用红包买家
        alipayinfo.setUseCoupon(params.get("use_coupon"));
        LOGGER.info("[use_coupon] = [" + params.get("use_coupon") + "]");
        //折扣
        alipayinfo.setDiscount(params.get("discount"));
        LOGGER.info("[discount] = [" + params.get("discount") + "]");
        //退款状态
        //0 : 未知
        if(StringUtil.isNull(params.get("refund_status"))) {
            alipayinfo.setRefundStatus(AliConstants.APP_ALIPAY_REFUND_STATUS_UNKNOWN_VALUE);
        } else {
            alipayinfo.setRefundStatus(params.get("refund_status"));
        }
        LOGGER.info("[refund_status] = [" + params.get("refund_status") + "]");
        //退款时间
        if(StringUtil.isNotNull(params.get("gmt_refund"))) {
            alipayinfo.setGmtRefund(DateUtil.toDate(params.get("gmt_refund")));
        }
        LOGGER.info("[gmt_refund] = [" + params.get("gmt_refund") + "]");
        // 逻辑删除 0:正常
        alipayinfo.setIsdel(0);

        return alipayinfo;
    }

}

package com.proper.enterprise.isj.order.controller;

import com.proper.enterprise.isj.order.entity.OrderEntity;
import com.proper.enterprise.isj.order.model.Order;
import com.proper.enterprise.isj.order.model.OrderConfirmReq;
import com.proper.enterprise.isj.order.model.OrderReq;
import com.proper.enterprise.isj.order.service.OrderService;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/order")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @PostMapping(value = "/createOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderReq orderReq) throws UnsupportedEncodingException {
        // 生成订单号
        String orderNo = RandomStringUtils.randomAlphabetic(32);

        // 设置订单信息
        Order orderInfo = new OrderEntity();
        // 支付状态 0:未支付
        orderInfo.setPaymentStatus(ConfCenter.getInt("isj.pay.paystatus.unpay"));
        // 订单号
        orderInfo.setOrderNo(orderNo);
        // 逻辑删除 0:正常
        orderInfo.setIsdel(ConfCenter.getInt("isj.pay.isdel.nomarl"));

        // 保存订单信息
        orderService.save(orderInfo);

        Map<String, String> retObj = new HashMap<>();

        retObj.put("resultCode", "0");
        retObj.put("orderNo", orderNo);
        LOGGER.info("retObj:" + retObj);

        return new ResponseEntity<>(retObj, HttpStatus.OK);

    }

    @PostMapping(value = "/updateOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> updateOrder(@RequestBody OrderReq orderReq) throws UnsupportedEncodingException {
        // 获取请求参数
        String paymentStatus = orderReq.getPaymentStatus();
        // 订单号
        String orderNo = orderReq.getOrderNo();
        // 获取订单信息
        Order orderInfo = orderService.findByOrderNo(orderNo);
        // 更新支付状态
        orderInfo.setPaymentStatus(Integer.parseInt(paymentStatus));
        // 更新订单信息
        orderService.save(orderInfo);

        Map<String, String> retObj = new HashMap<>();

        retObj.put("resultCode", "0");
        retObj.put("orderMessage", "SUCCESS");
        LOGGER.info("retObj:" + retObj);

        return new ResponseEntity<>(retObj, HttpStatus.OK);

    }

    /**
     * 查询订单异步通知结果
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/payStatus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getPayStatus(@RequestBody OrderConfirmReq orderConfirmReq) throws Exception {
        String orderNo = orderConfirmReq.getOrderNo();
        Order orderinfo = orderService.findByOrderNo(orderNo);
        Map<String, String> retObj = new HashMap<>();
        if(orderinfo != null) {
            // 未支付状态
            if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.unpay")) {
                retObj.put("paymentStatus", ConfCenter.get("isj.pay.paystatus.unpay"));
                // 支付待确认状态
            } else if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.unconfirmpay")) {
                retObj.put("paymentStatus", ConfCenter.get("isj.pay.paystatus.unconfirmpay"));
                // 已支付
            } else if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.payed")) {
                retObj.put("paymentStatus", ConfCenter.get("isj.pay.paystatus.payed"));
                // 支付失败 TODO 目前还不确认是否需要此状态
            } else if(orderinfo.getPaymentStatus() < ConfCenter.getInt("isj.pay.paystatus.payfailed")) {
                retObj.put("paymentStatus", ConfCenter.get("isj.pay.paystatus.payfailed"));
            }
            retObj.put("resultCode", "0");
        } else {
            retObj.put("paymentStatus", "ERROR");
            retObj.put("resultCode", "-1");
        }
        return new ResponseEntity<>(retObj, HttpStatus.OK);//TODO
    }
}

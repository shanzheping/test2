package com.proper.enterprise.isj.order.service;


import com.proper.enterprise.isj.order.model.Order;

public interface OrderService {

    Order save(Order order);

    Order findByOrderNo(String orderNo);
}

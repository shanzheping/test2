package com.proper.enterprise.isj.order.service.impl;


import com.proper.enterprise.isj.order.entity.OrderEntity;
import com.proper.enterprise.isj.order.model.Order;
import com.proper.enterprise.isj.order.repository.OrderRepository;
import com.proper.enterprise.isj.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepository orderRepo;

    @Override
    public Order save(Order order){
        return orderRepo.save((OrderEntity) order);
    }

    @Override
    public void save(Order... orders){
        List<OrderEntity> entities = new ArrayList<OrderEntity>();
        for(Order order : orders) {
            entities.add((OrderEntity) order);
        }
        orderRepo.save(entities);
    }

    @Override
    public Order findByOrderNo(String orderNo){
        return orderRepo.findByOrderNo(orderNo);
    }
}

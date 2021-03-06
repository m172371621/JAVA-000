package org.example.service;

import org.example.entity.OrderEntity;
import org.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public boolean addOrder(OrderEntity orderEntity) {
        return orderMapper.insert(orderEntity) > 0;
    }

    public OrderEntity selectById(Long id) {
        OrderEntity orderEntity = orderMapper.selectById(id);
        return orderEntity;
    }

}

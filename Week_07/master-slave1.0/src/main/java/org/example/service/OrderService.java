package org.example.service;

import org.example.config.DataSource;
import org.example.entity.OrderEntity;
import org.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @DataSource(name = "master")
    public boolean addOrder(OrderEntity orderEntity) {
        return orderMapper.insert(orderEntity) > 0;
    }

    @DataSource(name = "slave")
    public OrderEntity selectById(Long id) {
        return orderMapper.selectById(id);
    }

}

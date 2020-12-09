package org.example.controller;

import org.example.entity.OrderEntity;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("add")
    public void addOrder(@RequestBody OrderEntity orderEntity) {
        orderService.addOrder(orderEntity);
    }

    @GetMapping("selectById/{id}")
    public OrderEntity selectById(@PathVariable Long id) {
        return orderService.selectById(id);
    }

}

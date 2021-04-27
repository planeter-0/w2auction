package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.entity.Order;
import com.planeter.w2auction.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImp implements OrderService {
    @Override
    public void creatOrder(OrderDto orderDto) {
        Order order = new Order();
    }

    @Override
    public List<OrderDto> getMine(Long memberId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) {
        return null;
    }
}

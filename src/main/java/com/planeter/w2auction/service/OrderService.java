package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.Order;

import java.util.List;

public interface OrderService {
    /** 下单 */
    void creatOrder(OrderDto orderDto);
    /** 查看自己全部订单 */
    List<OrderDto> getMine(Long memberId);
    /** 订单详情 */
    Order getOrder(Long orderId);
}

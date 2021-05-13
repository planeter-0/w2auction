package com.planeter.w2auction.service;

import com.planeter.w2auction.dto.OrderFront;

import java.util.List;

public interface OrderService {
    /** 下单
     * @return*/
    boolean createOrder(OrderFront order);
    /** 查看自己全部订单 */
    List<OrderFront> getMine(Long userId, Integer type);
    /** 获取订单 */
    OrderFront getOrder(Long orderId);
    /** 管理员查看所有订单 */
    List<OrderFront> viewAll();
}

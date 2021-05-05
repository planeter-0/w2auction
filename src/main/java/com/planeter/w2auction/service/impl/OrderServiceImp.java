package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.DtoUtils;
import com.planeter.w2auction.dao.OrderDao;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.entity.OrderEntity;
import com.planeter.w2auction.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * @description: TODO
 * @author Planeter
 * @date 2021/4/28 16:29
 * @status dev
 */
@Service
public class OrderServiceImp implements OrderService {
    @Resource
    OrderDao orderDao;
    @Resource
    DtoUtils dtoUtils;
    @Override
    //v
    public void createOrder(OrderFront order) {
        orderDao.save(DtoUtils.toOrder(order));
    }
    //v
    @Override
    public List<OrderFront> getMine(Long buyerId) {
        List<OrderFront> fronts = new ArrayList<>();
        for(OrderEntity o:orderDao.findOrdersByBuyerId(buyerId)){
            fronts.add(dtoUtils.toOrderFront(o));
        }
        return fronts;
    }
    //v
    @Override
    public OrderFront getOrder(Long orderId) {
        return dtoUtils.toOrderFront(orderDao.getOne(orderId));
    }
    //v
    @Override
    public List<OrderFront> viewAll() {
        List<OrderFront> list = new ArrayList<>();
        for(OrderEntity o:orderDao.findAll()){
            list.add(dtoUtils.toOrderFront(o));
        }
        return list;
    }
}

package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.DtoUtils;
import com.planeter.w2auction.dao.ItemDao;
import com.planeter.w2auction.dao.OrderDao;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.entity.OrderEntity;
import com.planeter.w2auction.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/4/28 16:29
 * @status dev
 */
@Service
public class OrderServiceImp implements OrderService {
    @Resource
    OrderDao orderDao;
    @Resource
    ItemDao itemDao;
    @Resource
    DtoUtils dtoUtils;

    @Override
    //v
    public Long createOrder(OrderFront order) {

        //TODO jpql update
        Item item = itemDao.getOne(order.getItem().getId());
        Long orderEntityId = null;
        if (!item.isSold()) {
            orderEntityId = orderDao.save(DtoUtils.toOrder(order)).getId();
            item.setSold(true);
            itemDao.save(item);
        }
        return orderEntityId;
    }

    @Override
    public List<OrderFront> getMine(Long buyerId, Integer type) {
        List<OrderFront> fronts = new ArrayList<>();
        // 全部
        if (type == 2){
            for (OrderEntity o : orderDao.findOrdersByBuyerId(buyerId)) {
                fronts.add(dtoUtils.toOrderFront(o));
            }
            return fronts;
        }
        boolean complete = false;
        //未售出
        if (type == 0) {
            complete = false;
        } else if (type == 1) {//已售出
            complete = true;
        }
        for (OrderEntity o : orderDao.findOrdersByBuyerIdAndComplete(buyerId, complete)) {
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
        for (OrderEntity o : orderDao.findAll()) {
            list.add(dtoUtils.toOrderFront(o));
        }
        return list;
    }
}

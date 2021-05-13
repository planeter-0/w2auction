package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.enums.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.entity.Message;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.MessageService;
import com.planeter.w2auction.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Planeter
 * @description: order
 * @date 2021/4/29 20:57
 * @status ok
 */
@Slf4j
@RestController
public class OrderController {
    @Resource
    OrderService orderService;
    @Resource
    MessageService messageService;

    /**
     * 获取自己的订单
     *
     * @param type 0->未完成, 1->已完成, 2->全部
     * @return List<OrderFront>
     */
    @GetMapping("/order/mine")
    public ResponseData getMine(@RequestParam Integer type) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return new ResponseData(ExceptionMsg.SUCCESS, orderService.getMine(user.getId(), type));
    }

    /**
     * 创建订单, 推送消息
     *
     * @param front 订单
     * @return
     */
    @PostMapping("/order/create")
    public ResponseData create(@RequestBody OrderFront front) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        front.setBuyerId(user.getId());
        ItemFront item = front.getItem();
        //创建订单并修改item的isSold
        if (orderService.createOrder(front)) {
            //推送消息
            String content = new String("你的物品" + item.getName() + "已被" + user.getUsername() + "下单");
            messageService.push(new Message(item.getUsername(), content));
            return new ResponseData(ExceptionMsg.SUCCESS);
        }
        return new ResponseData(ExceptionMsg.Sold);
    }

    /**
     * 查看订单详情
     *
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/order/{orderId}")
    //只有订单所有者和管理员能看订单详情
    public ResponseData view(@PathVariable Long orderId) {
        Subject s = SecurityUtils.getSubject();
        if (s.isPermitted("order:view:" + orderId))
            return new ResponseData(ExceptionMsg.SUCCESS, orderService.getOrder(orderId));
        return new ResponseData(ExceptionMsg.NoSuchPermission);
    }
}

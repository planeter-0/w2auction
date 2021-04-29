package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: order
 * @author Planeter
 * @date 2021/4/29 20:57
 * @status dev
 */
@RestController
public class OrderController {
    @Resource
    OrderService orderService;

    @GetMapping("/order/mine")
    public ResponseData getMine() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return new ResponseData(ExceptionMsg.SUCCESS, orderService.getMine(user.getId()));
    }

    @PostMapping("/order/create")
    public ResponseData create(@RequestBody OrderFront front) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        orderService.createOrder(front);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
    @GetMapping("/order/{orderId}")
    //只有自己和管理员能看订单详情
    public ResponseData view(@PathVariable Long orderId){
        Subject s= SecurityUtils.getSubject();
        s.isPermitted("item:view:"+orderId);
        return new ResponseData(ExceptionMsg.SUCCESS,orderService.getOrder(orderId));
    }
}

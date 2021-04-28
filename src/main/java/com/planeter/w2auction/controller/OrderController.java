package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.Response;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Planeter
 * @description
 * @date 2021/4/28 15:56
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

    @PostMapping("/order/creat")
    public ResponseData creat(@RequestBody OrderFront front) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        orderService.creatOrder(front);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
    @GetMapping("/order/{orderId}")
    public ResponseData view(@PathVariable Long orderId){
        return new ResponseData(ExceptionMsg.SUCCESS,orderService.getOrder(orderId));
    }
}

package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.OrderService;
import com.planeter.w2auction.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Planeter
 * @description
 * @date 2021/4/27 10:47
 * @status dev
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    ItemService itemService;
    @Resource
    OrderService orderService;
    @Resource
    UserService userService;


    @GetMapping("/getItems")
    ResponseData viewItems() {
        //TODO 分页
        return new ResponseData(ExceptionMsg.SUCCESS,itemService.viewAll());
    }
    @GetMapping("/getOrders")
    ResponseData viewOrders(){
        return new ResponseData(ExceptionMsg.SUCCESS,orderService.viewAll());
    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping("/deleteUser")
    public ResponseData deleteUser(@RequestParam String username) {
        userService.delete(username);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

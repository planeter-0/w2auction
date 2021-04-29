package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.OrderService;
import com.planeter.w2auction.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: admin
 * @author Planeter
 * @date 2021/4/29 20:54
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
    @RequiresRoles("admin")
    ResponseData viewItems() {
        //TODO 分页
        return new ResponseData(ExceptionMsg.SUCCESS,itemService.viewAll());
    }
    @GetMapping("/getOrders")
    @RequiresRoles("admin")
    ResponseData viewOrders(){
        return new ResponseData(ExceptionMsg.SUCCESS,orderService.viewAll());
    }

    @DeleteMapping("/deleteUser")
    @RequiresRoles("admin")
    public ResponseData deleteUser(@RequestParam String username) {
        if(!userService.isAdminUser(username)) {
            userService.delete(username);
            return new ResponseData(ExceptionMsg.SUCCESS);
        }else{
            return new ResponseData(ExceptionMsg.NoSuchPermission);
        }
    }

    @PutMapping("/verify")
    @RequiresRoles("admin")
    ResponseData verify(@RequestParam Long itemId, @RequestParam boolean verified) {
        itemService.verify(itemId, verified);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

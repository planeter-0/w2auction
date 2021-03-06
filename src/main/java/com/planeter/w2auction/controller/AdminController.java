package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.enums.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.OrderService;
import com.planeter.w2auction.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Planeter
 * @description: admin
 * @date 2021/4/29 20:54
 * @status ok
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    ItemService itemService;
    @Resource
    OrderService orderService;
    @Resource
    UserService userService;

    /**
     * 获取所有物品
     * @return List<ItemFront> 所有物品
     */
    @GetMapping("/getItems")
    @RequiresRoles("admin")
    ResponseData viewItems() {
        //TODO 分页
        return new ResponseData(ExceptionMsg.SUCCESS, itemService.viewAll());
    }

    /**
     * 获取所有未审核物品
     * @return List<ItemFront> 所有物品
     */
    @GetMapping("/getUnVerified")
    @RequiresRoles("admin")
    ResponseData getUnVerifiedItems() {
        //TODO 分页
        return new ResponseData(ExceptionMsg.SUCCESS, itemService.getAllUnVerified());
    }

    /**
     * 获取所有订单
     * @return List<OrderFront> 所有订单
     */
    @GetMapping("/getOrders")
    @RequiresRoles("admin")
    ResponseData viewOrders() {
        return new ResponseData(ExceptionMsg.SUCCESS, orderService.viewAll());
    }

    /**
     * 禁用账户(非管理员用户)
     * @param username 用户名
     * @return
     */
    @DeleteMapping("/deleteUser")
    @RequiresRoles("admin")
    public ResponseData deleteUser(@RequestParam String username) {
        if (!userService.isAdminUser(username)) {
            userService.delete(username);
            userService.deleteJwtUser(username);//清除jwt用户,强制下线
            log.info("用户"+username+"被禁用");
            return new ResponseData(ExceptionMsg.SUCCESS);
        } else {
            return new ResponseData(ExceptionMsg.NoSuchPermission);
        }
    }

    /**
     * 审核物品, 修改物品的verified字段, 若未物品通过审核通知卖家
     * @param itemId 物品id
     * @param verified 是否通过审核 TODO 改为整形status, 0 未审核, 1 审核通过, 2 审核未通过
     * @return
     */
    @PutMapping("/verify")
    @RequiresRoles("admin")
    ResponseData verify(@RequestParam Long itemId, @RequestParam boolean verified) {
        itemService.verify(itemId, verified);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

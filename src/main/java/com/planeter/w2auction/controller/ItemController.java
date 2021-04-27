package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Planeter
 * @description: TODO 关键搜索
 * @date 2021/4/27 10:44
 * @status dev
 */
@RestController
public class ItemController {
    @Resource
    ItemService itemService;
    @Resource
    UserService userService;

    /**
     * 关键字搜索
     *
     * @param key
     * @return
     */
    @GetMapping("/search")
    ResponseData getAllVerifiedItem(@RequestParam String key) {
        //TODO elasticsearch 使用key进行关键字搜索
        return new ResponseData(ExceptionMsg.SUCCESS, itemService.search(key));
    }



    /**
     * 获取某个物品的详细信息v
     *
     * @param itemId
     * @return
     */
    @GetMapping("item/{itemId}")
    ResponseData getItems(@PathVariable Long itemId) {
        //TODO mybatis select 组装 itemDetail dto 减少连接
        List<Object> data = new ArrayList<>();
        Item i = itemService.getItem(itemId);
        data.add(i);
        data.add(userService.findByUsername(i.getUsername()));
        return new ResponseData(ExceptionMsg.SUCCESS,data);
    }

    /**
     * 上传商品v
     *
     * @param uploadItem
     * @return
     */
    @PostMapping("item/upload")
    ResponseData uploadItem(@RequestBody ItemFront uploadItem) {
        itemService.uploadItem(uploadItem);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }

    /**
     * 审核商品v
     *
     * @param itemId
     * @param verified
     * @return
     */
    @PutMapping("admin/verify")
    ResponseData verify(@RequestParam Long itemId, @RequestParam boolean verified) {
        itemService.verify(itemId, verified);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

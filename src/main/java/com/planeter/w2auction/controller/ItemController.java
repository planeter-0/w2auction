package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.UserService;
import com.planeter.w2auction.service.impl.EsItemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: item
 * @author Planeter
 * @date 2021/4/29 20:57
 * @status dev
 */
@RestController
public class ItemController {
    @Resource
    ItemService itemService;
    @Resource
    EsItemService esItemService;
    @Resource
    UserService userService;

    /**
     * 模糊搜索
     *
     * @param key 模糊词
     * @return  List<Map<String, Object>>
     */
    @GetMapping("/item/search")
    ResponseData getAllVerifiedItem(@RequestParam String key) throws IOException {
        //TODO elasticsearch 使用key进行关键字搜索
        return new ResponseData(ExceptionMsg.SUCCESS, esItemService.search(key));
    }


    /**
     * 物品的详细信息
     *
     * @param itemId 物品id
     * @return List<Object> 含Item和User
     */
    @GetMapping("/item/{itemId}")
    ResponseData getItems(@PathVariable Long itemId) {
        //TODO mybatis select 组装 itemDetail dto 减少数据库连接次数
        List<Object> data = new ArrayList<>();
        Item i = itemService.getItem(itemId);
        data.add(i);
        data.add(userService.findByUsername(i.getUsername()));
        return new ResponseData(ExceptionMsg.SUCCESS,data);
    }

    /**
     * 上传物品
     *
     * @param uploadItem 所上传的物品
     * @return
     */
    @PostMapping("/item/upload")
    ResponseData uploadItem(@RequestBody ItemFront uploadItem) {
        Item i = itemService.uploadItem(uploadItem);
        uploadItem.setId(i.getId());
        esItemService.add(uploadItem);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }

    /**
     * 删除物品
     * @param uploadItem 所删除的物品
     * @return
     */
    @DeleteMapping("item/delete")
    ResponseData deleteItem(@RequestBody ItemFront uploadItem) {
        if(uploadItem.isSold())
            return new ResponseData(ExceptionMsg.FAILED,"该物品已售出");
        Subject s= SecurityUtils.getSubject();
        s.isPermitted("item:delete:"+uploadItem.getId());
        itemService.deleteItem(uploadItem);
        esItemService.delete(uploadItem.getId());
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

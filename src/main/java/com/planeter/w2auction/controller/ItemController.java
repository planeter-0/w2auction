package com.planeter.w2auction.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.common.utils.DtoUtils;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.dto.UserFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.UserService;
import com.planeter.w2auction.service.impl.EsItemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Planeter
 * @description: item
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
     * @return List<Map < String, Object>>
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
        ItemFront i = DtoUtils.toItemFront(itemService.getItem(itemId));
        UserFront u = DtoUtils.toUserFront(userService.findByUsername(i.getUsername()));
        JSONObject itemJson = JSON.parseObject(JSONObject.toJSONString(i));
        JSONObject userJson = JSON.parseObject((JSONObject.toJSONString(u)));
        Map<String, JSONObject> result = new HashMap<>();
        result.put("item", itemJson);
        result.put("user", userJson);
        return new ResponseData(ExceptionMsg.SUCCESS, result);
    }

    /**
     * 上传物品
     *
     * @param uploadItem 所上传的物品
     * @return
     */
    @PostMapping("/item/upload")
    ResponseData uploadItem(@RequestBody ItemFront uploadItem) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
            User user = (User) subject.getPrincipals().getPrimaryPrincipal();
            uploadItem.setCreated(new Date());
            uploadItem.setUsername(user.getUsername());
            Item i = itemService.uploadItem(uploadItem);
            uploadItem.setId(i.getId());
            esItemService.add(uploadItem);
            return new ResponseData(ExceptionMsg.SUCCESS);
        }
        return new ResponseData(ExceptionMsg.FAILED);
    }

    /**
     * 删除物品
     *
     * @param itemId 所删除物品id
     * @return
     */
    @DeleteMapping("item/{itemId}")
    ResponseData deleteItem(@PathVariable Long itemId) {
        Item i = itemService.getItem(itemId);
        if (i.isSold())
            return new ResponseData(ExceptionMsg.FAILED, "该物品已售出");
        Subject s = SecurityUtils.getSubject();
        if (s.isPermitted("item:delete:" + itemId)) {
            itemService.deleteById(itemId);
            esItemService.delete(itemId);
            return new ResponseData(ExceptionMsg.SUCCESS);
        }
        return new ResponseData(ExceptionMsg.NoSuchPermission);
    }
}

package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.UploadItem;
import com.planeter.w2auction.service.ItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ItemController {
    @Resource
    ItemService itemService;

    /**
     * 获取所有通过审核的物品info
     * @param key
     * @return
     */
    @GetMapping("/getItems")
    ResponseData getAllItem(@RequestParam String key){
        return new ResponseData(ExceptionMsg.SUCCESS,itemService.getAllVerified());
    }

    /**
     * 获取某个物品的详细信息
     * @param itemId
     * @return
     */
    @GetMapping("item/{itemId}")
    ResponseData getItems(@PathVariable Integer itemId){
        return new ResponseData(ExceptionMsg.SUCCESS,itemService.getDetailedItem(itemId));
    }
    @PostMapping("item/upload")
    ResponseData uploadItem(@RequestBody UploadItem uploadItem){
        itemService.uploadItem(uploadItem);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

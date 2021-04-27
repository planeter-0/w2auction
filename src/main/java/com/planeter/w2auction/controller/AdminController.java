package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/getItems")
    ResponseData getAllItems() {
        //TODO 分页
        return new ResponseData(ExceptionMsg.SUCCESS,itemService.viewAll());
    }
    @GetMapping("")
}

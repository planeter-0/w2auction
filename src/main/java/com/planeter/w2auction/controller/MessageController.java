package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.enums.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.MessageService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/7 10:11
 * @status dev
 */
@RestController
public class MessageController {
    @Resource
    MessageService messageService;
    @GetMapping("/message/pull")
    public ResponseData pullMine(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return new ResponseData(ExceptionMsg.SUCCESS,messageService.pull(user.getUsername()));
    }
}

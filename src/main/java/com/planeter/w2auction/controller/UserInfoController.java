package com.planeter.w2auction.controller;


import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;

import com.planeter.w2auction.service.UserService;
import org.apache.shiro.SecurityUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
/**
 * 会员,管理员的登出
 */
public class UserInfoController {
    @Resource
    UserService userInfoService;

    /**
     * 登出
     * @return
     */
    @PutMapping("/logout")
    public ResponseData logout() {
        SecurityUtils.getSubject().logout();
        return new ResponseData(ExceptionMsg.SUCCESS,"已登出");
    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping("admin/deleteUser")
    public ResponseData deleteUserInfo(Integer id) {
        userInfoService.deleteUser(id);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

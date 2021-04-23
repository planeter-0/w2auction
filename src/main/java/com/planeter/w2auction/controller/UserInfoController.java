package com.planeter.w2auction.controller;


import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;

import com.planeter.w2auction.service.UserInfoService;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
/**
 * 会员,管理员的登入和登出
 */
public class UserInfoController {
    @Resource
    UserInfoService userInfoService;

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
    @RequiresPermissions("user:delete")
    public ResponseData deleteUserInfo(Integer id) {
        userInfoService.deleteUserInfo(id);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

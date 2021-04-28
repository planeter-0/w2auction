package com.planeter.w2auction.controller;

/**
 * @author Planeter
 * @description
 * @date 2021/4/28 16:40
 * @status dev
 */

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserService userService;

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
        userService.deleteUser(id);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}

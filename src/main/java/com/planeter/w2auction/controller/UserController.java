package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.enums.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.UserFront;
import com.planeter.w2auction.dto.UserInfo;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: user
 * @author Planeter
 * @date 2021/4/29 20:57
 * @status ok
 */
@RestController
public class UserController {
    @Resource
    UserService userService;
    /**
     * 学号密码注册
     *
     * @param info
     * @return
     */

@PostMapping("/register")
    public ResponseData register(@RequestBody UserInfo info) {
        //取得注册用户的用户名和密码
        String username = info.getUsername();
        String password = info.getPassword();
        //检查学号是否已经存在
        if (userService.isValid(username)) {
            return new ResponseData(ExceptionMsg.UserNameUsed);
        }
        userService.register(username,password);//密码bcrypt加密存入数据库
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
    /**
     * 登陆, DbRealm处理,返回jwt
     * @param info
     * @return
     */
    @PostMapping("/login")
    public ResponseData login(@RequestBody UserInfo info, HttpServletResponse response) {
        String username = info.getUsername();
        String password = info.getPassword();
        Subject subject = SecurityUtils.getSubject();
        String jwtToken = null;
        try {
            //UsernamePasswordToken交给DbShiroRealm进行凭证匹配
            subject.login(new UsernamePasswordToken(username, password));
            //生成JwtToken并存储生成用的salt
            jwtToken = userService.generateJwtToken(username);
            response.setHeader("x-auth-token",jwtToken);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return new ResponseData(ExceptionMsg.UserNameWrong);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return new ResponseData(ExceptionMsg.PasswordWrong);
        } catch (Exception e) {
            return new ResponseData(ExceptionMsg.FAILED);
        }
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
    /**
     * 登出
     * @return
     */
    @PutMapping("/logout")
    public ResponseData logout() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
            User user = (User) subject.getPrincipals().getPrimaryPrincipal();
            userService.deleteJwtUser(user.getUsername());
        }
        SecurityUtils.getSubject().logout();
        return new ResponseData(ExceptionMsg.SUCCESS,"已登出");
    }
    /**
     * 更新信息
     * @param front
     * @return
     */
    @PutMapping("/user/update")
    public ResponseData updateMember(@RequestBody UserFront front){
        Subject subject = SecurityUtils.getSubject();
        User user;
        if(subject.getPrincipals() != null) {
            user = (User) subject.getPrincipals().getPrimaryPrincipal();
            user.setNickname(front.getNickname());
            user.setGender(front.getGender());
            user.setImageId(front.getImageId());
            userService.save(user);
            return new ResponseData(ExceptionMsg.SUCCESS);
        }
        return new ResponseData(ExceptionMsg.FAILED);
    }

    /**
     * 更新密码
     * @param newPsw
     * @return
     */
    @PutMapping("/user/setPassword")
    public ResponseData setPassword(@RequestParam String newPsw){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
            User user = (User) subject.getPrincipals().getPrimaryPrincipal();
            userService.setPassword(user.getUsername(), newPsw);
            // 强制登出
            userService.deleteJwtUser(user.getUsername());
            subject.logout();
            return new ResponseData(ExceptionMsg.SUCCESS);
        }
        return new ResponseData(ExceptionMsg.FAILED);
    }
}

package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.JwtUtils;
import com.planeter.w2auction.dao.RoleDao;
import com.planeter.w2auction.dao.UserDao;
import com.planeter.w2auction.entity.*;
import com.planeter.w2auction.service.MessageService;
import com.planeter.w2auction.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
    @Resource
    RoleDao roleDao;
    @Resource
    MessageService messageService;

    @Override
    public boolean isValid(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public User register(String username, String password) {
        User user = new User();
        try {
            //Bcrypt加密
            String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            //创建user
            List<Role> roles = new ArrayList<>();
            roles.add(roleDao.findByName("user"));//
            user = new User(username, encodedPassword, 1, roles);
            //写入数据库
            userDao.save(user);//插入
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(String username) {
        userDao.deleteByUsername(username);
        messageService.push(new Message("Your account have been delete", username));
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据用户名+时间戳+盐生成JWTToken
     */
    public String generateJwtToken(String username) {
        String salt = JwtUtils.generateSalt();
        userDao.updateSalt(username, salt);
        //TODO 将生成jwt用的salt存入缓存
        //redisTemplate.opsForValue().set("token:"+username, salt, 3000, TimeUnit.SECONDS);
        return JwtUtils.sign(username, salt, 3600); //生成jwt token，设置过期时间为3000s
    }

    @Override
    public User getJwtUser(String username) {
        //TODO 从缓存中取出jwt token生成时用的salt
        //salt = redisTemplate.opsForValue().get("token:"+username);
        return findByUsername(username);
    }

    @Override
    public boolean isAdminUser(String username) {
        return userDao.findByUsername(username).getRoles().contains(roleDao.findByName("admin"));
    }

}

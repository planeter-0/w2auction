package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.JwtUtils;
import com.planeter.w2auction.dao.UserInfoDao;
import com.planeter.w2auction.entity.*;
import com.planeter.w2auction.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserService {

    @Resource
    UserInfoDao userInfoDao;

    @Override
    public User findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }

    @Override
    public boolean isValid(String username) {
        return userInfoDao.existsByUsername(username);
    }

    @Override
    public User registerUser(User userInfo) {
        return userInfoDao.save(userInfo);
    }

    /**
     * 用户名+时间戳生成JWTToken
     */
    public String generateJwtToken(String username) {
        //TODO 随机生成盐
        String salt = "12345";//JwtUtils.generateSalt();
        /**
         * @todo 将salt保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
         */
        return JwtUtils.sign(username, salt, 3600); //生成jwt token，设置过期时间为1小时
    }
    public User getJwtUserInfo(String username) {
        String salt = "12345";
        /**
         * @todo 从数据库或者缓存中取出jwt token生成时用的salt
         * salt = redisTemplate.opsForValue().get("token:"+username);
         */
        User userInfo = findByUsername(username);
        userInfo.setSalt(salt);
        return userInfo;
    }


    @Override
    public List<String> getRolesByUsername(String username) {
        List<Role> roleList = userInfoDao.findByUsername(username).getRoles();
        List<String> ret = new ArrayList<>();
        for (Role role : roleList) {
            ret.add(role.getName());
        }
        return ret;
    }

    @Override
    public List<String> getPermissionsByUsername(String username) {
        // 获取角色权限
        User userInfo = userInfoDao.findByUsername(username);
        List<Permission> rolePermissions = new ArrayList<>();
        List<String> stringPermissions = new ArrayList<>();//字符串权限列表
        for (Role role : userInfo.getRoles()) {
            rolePermissions.addAll(role.getPermissions());
        }
        for (Permission permission : rolePermissions) {
            stringPermissions.add(permission.getName()); //角色权限
        }
        //获取Member的实例级权限
        String itemUpdate = "item:update:";
        String itemDelete = "item:delete:";
        String orderView = "order:view:";
        Member member = userInfo.getMember();
        if (member != null) {
            // 加入item权限
            List<Item> items = member.getItems();
            for (Item item : items) {
                stringPermissions.add(itemUpdate + item.getId());
                stringPermissions.add(itemDelete + item.getId());
            }
            // 加入order权限
//            List<Order> orders = member.getBuyerOrders();
//            orders.addAll(member.getSellerOrders());
//            for (Order order : orders) {
//                stringPermissions.add(orderView + order.getId());
//            }
        }
        return stringPermissions;
    }

    @Override
    public void deleteUser(Integer id) {
        userInfoDao.deleteById(id.longValue());
    }
}

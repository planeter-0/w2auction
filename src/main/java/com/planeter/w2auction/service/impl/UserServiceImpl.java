package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.JwtUtils;
import com.planeter.w2auction.dao.RoleDao;
import com.planeter.w2auction.dao.UserDao;
import com.planeter.w2auction.entity.*;
import com.planeter.w2auction.service.MessageService;
import com.planeter.w2auction.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String generateJwtToken(String username) {
        //TODO 随机生成盐
        String salt = "12345";//JwtUtils.generateSalt();
         // @todo 将salt保存到数据库或者缓存中
         //redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);

        return JwtUtils.sign(username, salt, 3600); //生成jwt token，设置过期时间为1小时
    }

    @Override
    public User getJwtUser(String username) {
        //主要是获取这个盐,本来该在redis里
        String salt = "12345";
        /*
         * TODO 从数据库或者缓存中取出jwt token生成时用的salt
         * salt = redisTemplate.opsForValue().get("token:"+username);
         */
        User user = findByUsername(username);
        user.setSalt(salt);
        return user;
    }

    @Override
    public boolean isAdminUser(String username) {
        return userDao.findByUsername(username).getRoles().contains(roleDao.findByName("admin"));
    }


//
//    @Override
//    public List<String> getPermissionsByUsername(String username) {
//        // 获取角色权限
//        User userInfo = userInfoDao.findByUsername(username);
//        List<Permission> rolePermissions = new ArrayList<>();
//        List<String> stringPermissions = new ArrayList<>();//字符串权限列表
//        for (Role role : userInfo.getRoles()) {
//            rolePermissions.addAll(role.getPermissions());
//        }
//        for (Permission permission : rolePermissions) {
//            stringPermissions.add(permission.getName()); //角色权限
//        }
//        //获取Member的实例级权限
//        String itemUpdate = "item:update:";
//        String itemDelete = "item:delete:";
//        String orderView = "order:view:";
//        Member member = userInfo.getMember();
//        if (member != null) {
//            // 加入item权限
//            List<Item> items = member.getItems();
//            for (Item item : items) {
//                stringPermissions.add(itemUpdate + item.getId());
//                stringPermissions.add(itemDelete + item.getId());
//            }
//            // 加入order权限
////            List<Order> orders = member.getBuyerOrders();
////            orders.addAll(member.getSellerOrders());
////            for (Order order : orders) {
////                stringPermissions.add(orderView + order.getId());
////            }
//        }
//        return stringPermissions;
//    }
}

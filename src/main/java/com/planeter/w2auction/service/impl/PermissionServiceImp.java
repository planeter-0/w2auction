package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.dao.UserDao;
import com.planeter.w2auction.entity.Permission;
import com.planeter.w2auction.entity.Role;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Planeter
 * @description
 * @date 2021/4/29 19:29
 * @status dev
 */
@Service
public class PermissionServiceImp implements PermissionService {
    @Resource
    UserDao userDao;
    private static final List<String> dynamicItemPermissions = new ArrayList<>();
    private static final List<String> dynamicOrderPermissions = new ArrayList<>();

    static {
        dynamicItemPermissions.add("item:delete:");
        dynamicItemPermissions.add("item:update:");
        dynamicOrderPermissions.add("order:view:");
        dynamicOrderPermissions.add("order:update:");
    }
    //TODO 缓存用户权限
    @Override
    public List<String> getPermissionsByUsername(User user) {
        List<String> permissionStrList = new ArrayList<>();
        //Role Permissions
        for (Role r : user.getRoles()) {
            for (Permission p : r.getPermissions())
                permissionStrList.add(p.getName());
        }
        //Instance Permissions
        permissionStrList.addAll(getDynamicPermissions(user));
        return permissionStrList;
    }

    public List<String> getDynamicPermissions(User user) {
        List<String> permissionStrList = new ArrayList<>();
        List<Long> itemIds = userDao.getItemIds(user.getUsername());
        List<Long> orderIds = userDao.getOrderIds(user.getId());
        for (String s : dynamicItemPermissions) {
            for (Long l : itemIds) {
                permissionStrList.add(s + l);
            }
        }
        for (String s : dynamicOrderPermissions) {
            for (Long l : orderIds) {
                permissionStrList.add(s + l);
            }
        }
        return permissionStrList;
    }
}

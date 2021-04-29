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
    private static final List<String> dynamicPermissions = new ArrayList<>();

    static {
        dynamicPermissions.add("item:delete:");
        dynamicPermissions.add("item:update:");
        dynamicPermissions.add("order:view:");
        dynamicPermissions.add("order:update:");
    }

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
        List<Long> instanceIds = new ArrayList<>();
        instanceIds.addAll(userDao.getOrderIds(user.getId()));
        instanceIds.addAll(userDao.getItemIds(user.getUsername()));
        Iterator<Long> idIterator = instanceIds.stream().iterator();
        for (String s : dynamicPermissions) {
            while (idIterator.hasNext()) {
                permissionStrList.add(s + idIterator.next());
            }
        }
        return permissionStrList;
    }
}

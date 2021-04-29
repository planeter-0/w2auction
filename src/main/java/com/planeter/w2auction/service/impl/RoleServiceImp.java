package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.dao.RoleDao;
import com.planeter.w2auction.dao.UserDao;
import com.planeter.w2auction.entity.Role;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.RoleService;
import org.apache.shiro.SecurityUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Planeter
 * @description
 * @date 2021/4/29 9:28
 * @status dev
 */
public class RoleServiceImp implements RoleService {
    @Resource
    UserDao userDao;
    @Resource
    RoleDao roleDao;
    @Override
    public List<String> getRoles() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roleList = user.getRoles();
        List<String> ret = new ArrayList<>();
        for (Role role : roleList) {
            ret.add(role.getName());
        }
        return ret;
    }

    @Override
    public void addRoles(List<Role> newRoles) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = user.getRoles();
        roles.addAll(newRoles);
        user.setRoles(roles);
        userDao.save(user);
    }
}

package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.Role;

import java.util.List;

public interface RoleService {
    /** 根据用户名获取角色 */
    public List<String> getRoles();
    /** 增加角色 */
    public void addRoles(List<Role> roleList);
}

package com.planeter.w2auction.service;

import java.util.List;

public interface RoleService {
    /** 根据用户名获取角色 */
    public List<String> getRolesByUsername(String username);
    /** 增加角色 */
    public void addRoles();
}

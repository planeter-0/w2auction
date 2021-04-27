package com.planeter.w2auction.service;

import java.util.List;

public interface PermissionService {
    /** 根据用户名获取细分权限 */
    /** 获取权限 */
    public List<String> getPermissionsByUsername(String username);
}

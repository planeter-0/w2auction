package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.User;

import java.util.List;

public interface PermissionService {
    /** 获取权限 */
    List<String> getPermissionsByUsername(User user);
}

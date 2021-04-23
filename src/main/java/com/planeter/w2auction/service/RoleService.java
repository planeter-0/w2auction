package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.SysRole;


public interface RoleService {
    /** 通过name找到Role */
    public SysRole findByName(String name);
    /** 检查username是否已存在 */
    public boolean isValid(String name);
}

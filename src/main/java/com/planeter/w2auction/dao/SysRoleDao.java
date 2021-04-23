package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleDao extends JpaRepository<SysRole, Long> {
    SysRole findByName(String name);
}
